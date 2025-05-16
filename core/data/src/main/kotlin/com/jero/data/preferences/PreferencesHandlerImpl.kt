package com.jero.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.domain.preferences.PreferencesHandler
import com.jero.core.utils.emptyString

class PreferencesHandlerImpl(context: Context) : PreferencesHandler {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    private val edit = sharedPreferences.edit()

    override fun clear() {
        edit.clear().apply()
    }

    companion object {
        private const val DATABASE_URI = "databaseUri"
        private const val IS_LOGGED = "isLogged"
    }

    override var databaseUri: String?
        get() = sharedPreferences.getString(DATABASE_URI, emptyString())
        set(value) {
            this.edit.putString(DATABASE_URI, value)?.apply()
        }

    override var isLogged: Boolean
        get() = sharedPreferences.getBoolean(IS_LOGGED, false)
        set(value) {
            this.edit.putBoolean(IS_LOGGED, value)?.apply()
        }
}
