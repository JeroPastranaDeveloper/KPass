package com.jero.navigation

import com.jero.model.KPass
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

sealed interface KPassScreen {
    @Serializable
    data object Home : KPassScreen

    @Serializable
    data object SelectDatabase : KPassScreen
/*// TODO
    @Serializable
    data class Renombrar(val kpass: KPass) : KPassScreen {
        companion object {
            val RenombrarTambien = mapOf(typeOf<KPass>() to KPassType)
        }
    }*/
}
