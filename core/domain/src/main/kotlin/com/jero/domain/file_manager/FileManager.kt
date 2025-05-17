package com.jero.domain.file_manager

import android.content.Context
import android.net.Uri
import com.jero.core.model.Account

interface FileManager {
    fun readAccounts(context: Context, uri: Uri): List<Account>
    fun writeAccounts(context: Context, uri: Uri, accounts: List<Account>)
    fun upsertAccount(context: Context, uri: Uri, account: Account)
    fun readAccountById(context: Context, uri: Uri, accountId: String): Account?
    fun deleteAccount(context: Context, uri: Uri, accountId: String): List<Account>
}
