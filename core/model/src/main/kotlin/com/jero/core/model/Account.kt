package com.jero.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Account(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val email: String = "",
    val password: String = "",
) : Parcelable
