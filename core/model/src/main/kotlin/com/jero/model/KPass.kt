package com.jero.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

// TODO
@Serializable
@Parcelize
data class KPass(
    val kpass: Boolean
) : Parcelable