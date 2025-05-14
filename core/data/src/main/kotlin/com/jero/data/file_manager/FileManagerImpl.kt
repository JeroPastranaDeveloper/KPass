package com.jero.data.file_manager

import android.content.Context
import android.net.Uri
import com.example.domain.file_manager.FileManager
import com.google.gson.Gson
import com.jero.core.model.Account

class FileManagerImpl : FileManager {
    override fun readAccounts(context: Context, uri: Uri): List<Account> =
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val json = inputStream.bufferedReader().use { it.readText() }
            if (json.isBlank()) emptyList() else Gson().fromJson(json, Array<Account>::class.java).toList()
        }.orEmpty()

    override fun writeAccounts(context: Context, uri: Uri, accounts: List<Account>) {
        context.contentResolver.openOutputStream(uri, "wt")?.use { outputStream ->
            val json = Gson().toJson(accounts)
            outputStream.write(json.toByteArray())
        }
    }

    override fun upsertAccount(context: Context, uri: Uri, account: Account) {
        val currentAccounts = readAccounts(context, uri).toMutableList()
        val updatedAccounts = if (currentAccounts.any { it.id == account.id }) {
            currentAccounts.map { if (it.id == account.id) account else it }
        } else {
            currentAccounts + account
        }
        writeAccounts(context, uri, updatedAccounts)
    }

    override fun readAccountById(context: Context, uri: Uri, accountId: String): Account? =
        readAccounts(context, uri).find { it.id == accountId }

    override fun deleteAccount(context: Context, uri: Uri, accountId: String): List<Account> {
        val updatedAccounts = readAccounts(context, uri).filterNot { it.id == accountId }
        writeAccounts(context, uri, updatedAccounts)
        return updatedAccounts
    }
}
