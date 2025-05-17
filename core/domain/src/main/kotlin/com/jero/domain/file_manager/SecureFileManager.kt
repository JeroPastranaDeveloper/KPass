package com.jero.domain.file_manager

import android.content.Context
import android.net.Uri
import com.jero.core.model.Account

interface SecureFileManager {
    fun encryptAndSave(context: Context, uri: Uri, password: String, accounts: List<Account>)
    fun decryptAndRead(context: Context, uri: Uri, password: String): List<Account>?
}
