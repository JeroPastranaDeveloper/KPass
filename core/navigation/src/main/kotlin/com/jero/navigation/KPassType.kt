package com.jero.navigation

import android.net.Uri
import android.os.Bundle
import androidx.core.os.BundleCompat
import androidx.navigation.NavType
import com.jero.core.model.Account
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object KPassType : NavType<Account>(isNullableAllowed = false) {

    override fun put(bundle: Bundle, key: String, value: Account) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): Account? =
        BundleCompat.getParcelable(bundle, key, Account::class.java)

    override fun parseValue(value: String): Account {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: Account): String = Uri.encode(Json.encodeToString(value))
}
