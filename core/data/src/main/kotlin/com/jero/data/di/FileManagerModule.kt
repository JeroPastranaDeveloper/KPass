package com.jero.data.di

import com.example.domain.file_manager.FileManager
import com.jero.data.file_manager.FileManagerImpl
import org.koin.dsl.module

val fileManagerModule = module {
    single<FileManager> { FileManagerImpl() }
}
