package com.jero.select_database.di

import com.jero.select_database.SelectDatabaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SelectDatabaseViewModelModule = module {
    viewModel { SelectDatabaseViewModel(get(), get()) }
}
