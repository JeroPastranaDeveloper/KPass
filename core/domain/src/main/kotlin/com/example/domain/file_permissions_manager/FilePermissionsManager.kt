package com.example.domain.file_permissions_manager

import android.content.Context
import android.net.Uri

interface FilePermissionManager {
    fun checkPermissions(context: Context, uri: Uri)
    fun initializeDatabaseFile(context: Context, uri: Uri)
}
