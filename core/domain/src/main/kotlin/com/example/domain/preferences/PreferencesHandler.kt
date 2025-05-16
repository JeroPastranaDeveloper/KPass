package com.example.domain.preferences

interface PreferencesHandler {
    var databaseUri: String?
    var isLogged: Boolean
    fun clear()
}