package com.jero.kpass

import android.app.Application
import com.jero.add_edit_account.di.addEditAccountViewModelModule
import com.jero.data.di.fileManagerModule
import com.jero.data.di.filePermissionManagerModule
import com.jero.data.di.preferencesModule
import com.jero.home.di.accountsViewModelModule
import com.jero.navigation.navigationModule
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
                accountsViewModelModule,
                addEditAccountViewModelModule,
                SelectDatabaseViewModelModule,
                preferencesModule,
                fileManagerModule,
                filePermissionManagerModule,
            )
        }
    }
}
