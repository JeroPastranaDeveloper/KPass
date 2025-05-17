package com.jero.data.di

import com.jero.data.file_manager.EncryptedFileManagerImpl
import com.jero.data.file_permissions_manager.SecureFileManagerImpl
import com.jero.domain.file_manager.FileManager
import com.jero.domain.file_manager.SecureFileManager
import org.koin.dsl.module

val fileManagerModule = module {
    single<FileManager> { EncryptedFileManagerImpl(get(), get()) }
}

val secureFileManagerModule = module {
    single<SecureFileManager> { SecureFileManagerImpl() }
}
