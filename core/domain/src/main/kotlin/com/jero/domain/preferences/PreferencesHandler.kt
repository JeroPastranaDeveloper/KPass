package com.jero.domain.preferences

interface PreferencesHandler {
    var databaseUri: String?
    var isLogged: Boolean
    fun clear()
}