package com.jero.data.di

import com.jero.data.file_permissions_manager.FilePermissionManagerImpl
import com.jero.domain.file_permissions_manager.FilePermissionManager
import org.koin.dsl.module

val filePermissionManagerModule = module {
    single<FilePermissionManager> { FilePermissionManagerImpl(get()) }
}
