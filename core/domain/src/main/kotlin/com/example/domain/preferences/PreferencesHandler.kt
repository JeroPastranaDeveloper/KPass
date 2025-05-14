package com.example.domain.preferences

interface PreferencesHandler {
    var databaseUri: String?
    fun clear()
}