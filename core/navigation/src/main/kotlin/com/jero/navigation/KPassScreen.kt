package com.jero.navigation

import androidx.navigation.NavType
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

sealed interface KPassScreen {
    @Serializable
    data object Home : KPassScreen

    @Serializable
    data object SelectDatabase : KPassScreen

    @Serializable
    data object Accounts : KPassScreen

    @Serializable
    data class AddEditAccount(val id: String) : KPassScreen {
        companion object {
            val typeMap = mapOf(
                typeOf<String>() to NavType.StringType
            )
        }
    }

    @Serializable
    data class AccountDetail(val id: String) : KPassScreen {
        companion object {
            val typeMap = mapOf(
                typeOf<String>() to NavType.StringType
            )
        }
    }
}
