package com.jero.data.di

import com.jero.data.preferences.PreferencesHandlerImpl
import com.jero.domain.preferences.PreferencesHandler
import org.koin.dsl.module

val preferencesModule = module {
    single<PreferencesHandler> { PreferencesHandlerImpl(get()) }
}