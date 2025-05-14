package com.jero.kpass

import android.app.Application
import com.jero.navigation.navigationModule
import com.jero.data.di.databaseModule
import com.jero.home.di.accountsViewModelModule
import com.jero.add_edit_account.di.addEditAccountViewModelModule
import com.jero.data.di.preferencesModule
import com.jero.select_database.di.SelectDatabaseViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                navigationModule,
                databaseModule,
                accountsViewModelModule,
                addEditAccountViewModelModule,
                SelectDatabaseViewModelModule,
                preferencesModule,
            )
        }
    }
}
