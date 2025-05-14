package com.jero.data.di

import androidx.room.Room
import com.jero.data.repository.roomdatabase.DeleteAccountImpl
import com.jero.data.repository.roomdatabase.GetAccountsRepositoryImpl
import com.jero.data.repository.roomdatabase.SaveAccountRepositoryImpl
import com.example.database.KPassDatabase
import com.example.domain.repository.roomdatabase.DeleteAccount
import com.example.domain.repository.roomdatabase.GetAccountById
import com.example.domain.repository.roomdatabase.GetAccountsRepository
import com.example.domain.repository.roomdatabase.SaveAccountRepository
import com.jero.data.repository.roomdatabase.GetAccountByIdImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            KPassDatabase::class.java,
            "KPass.db"
        ).fallbackToDestructiveMigration(false).build()
    }

    single { get<KPassDatabase>().accountsDao() }

    single<SaveAccountRepository> {
        SaveAccountRepositoryImpl(
            get()
        )
    }

    single<GetAccountsRepository> {
        GetAccountsRepositoryImpl(
            get()
        )
    }

    single<DeleteAccount> {
        DeleteAccountImpl(get())
    }

    single<GetAccountById> {
        GetAccountByIdImpl(get())
    }
}
