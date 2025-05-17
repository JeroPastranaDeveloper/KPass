package com.jero.data.di

import com.jero.data.session.SessionManagerImpl
import com.jero.domain.session.SessionManager
import org.koin.dsl.module

val sessionManagerModule = module {
    single<SessionManager> { SessionManagerImpl }
}
