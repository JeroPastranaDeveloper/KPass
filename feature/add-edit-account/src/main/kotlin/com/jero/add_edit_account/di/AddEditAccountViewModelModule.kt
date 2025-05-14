package com.jero.add_edit_account.di

import com.jero.add_edit_account.AddEditAccountViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val addEditAccountViewModelModule = module {
    viewModel { AddEditAccountViewModel(get(), get(), get()) }
}
