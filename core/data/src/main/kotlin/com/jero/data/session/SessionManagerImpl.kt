package com.jero.data.session

import com.jero.domain.session.SessionManager

object SessionManagerImpl : SessionManager {
    override var databasePassword: String? = null
}
