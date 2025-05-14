package com.jero.data.di

import com.example.domain.file_permissions_manager.FilePermissionManager
import com.jero.data.file_permissions_manager.FilePermissionManagerImpl
import org.koin.dsl.module

val filePermissionManagerModule = module {
    single<FilePermissionManager> { FilePermissionManagerImpl() }
}
