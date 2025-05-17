package com.jero.data.file_manager

import android.content.Context
import android.net.Uri
import com.jero.core.model.Account
import com.jero.domain.file_manager.FileManager
import com.jero.domain.file_manager.SecureFileManager
import com.jero.domain.session.SessionManager

class EncryptedFileManagerImpl(
    private val secureFileManager: SecureFileManager,
    private val sessionManager: SessionManager
) : FileManager {

    override fun readAccounts(context: Context, uri: Uri): List<Account> {
        val password = sessionManager.databasePassword.orEmpty()
        return secureFileManager.decryptAndRead(context, uri, password).orEmpty()
    }

    override fun writeAccounts(context: Context, uri: Uri, accounts: List<Account>) {
        val password = sessionManager.databasePassword ?: return
        secureFileManager.encryptAndSave(context, uri, password, accounts)
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

    override fun readAccountById(context: Context, uri: Uri, accountId: String): Account? {
        return readAccounts(context, uri).find { it.id == accountId }
    }

    override fun deleteAccount(context: Context, uri: Uri, accountId: String): List<Account> {
        val updatedAccounts = readAccounts(context, uri).filterNot { it.id == accountId }
        writeAccounts(context, uri, updatedAccounts)
        return updatedAccounts
    }
}
