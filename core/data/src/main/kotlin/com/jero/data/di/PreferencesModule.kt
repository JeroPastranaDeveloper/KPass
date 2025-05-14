package com.jero.data.di

import com.example.domain.preferences.PreferencesHandler
import com.jero.data.preferences.PreferencesHandlerImpl
import org.koin.dsl.module

val preferencesModule = module {
    single<PreferencesHandler> { PreferencesHandlerImpl(get()) }
}