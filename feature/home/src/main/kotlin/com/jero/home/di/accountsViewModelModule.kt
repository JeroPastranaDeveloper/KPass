package com.jero.home.di

import com.jero.home.AccountsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val accountsViewModelModule = module {
    viewModel { AccountsViewModel(get(), get()) }
}
