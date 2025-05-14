package com.jero.data.file_permissions_manager

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.domain.file_permissions_manager.FilePermissionManager

class FilePermissionManagerImpl : FilePermissionManager {
    override fun checkPermissions(context: Context, uri: Uri) {
        val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        context.contentResolver.takePersistableUriPermission(uri, flags)
    }

    override fun initializeDatabaseFile(context: Context, uri: Uri) {
        context.contentResolver.openOutputStream(uri, "wt")?.use { outputStream ->
            val emptyJson = "[]"
            outputStream.write(emptyJson.toByteArray())
        }
    }
}
