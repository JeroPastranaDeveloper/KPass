package com.jero.navigation

import android.net.Uri
import android.os.Bundle
import androidx.core.os.BundleCompat
import androidx.navigation.NavType
import com.jero.model.KPass
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object KPassType : NavType<KPass>(isNullableAllowed = false) {

    override fun put(bundle: Bundle, key: String, value: KPass) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): KPass? =
        BundleCompat.getParcelable(bundle, key, KPass::class.java)

    override fun parseValue(value: String): KPass {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: KPass): String = Uri.encode(Json.encodeToString(value))
}
