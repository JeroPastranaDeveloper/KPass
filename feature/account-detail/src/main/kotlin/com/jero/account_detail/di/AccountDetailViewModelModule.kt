package com.jero.account_detail.di

import com.jero.account_detail.AccountDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val accountDetailViewModelModule = module {
    viewModel { AccountDetailViewModel(get(), get()) }
}
