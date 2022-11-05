package com.internal.data.remote.model.rates

import kotlinx.serialization.Serializable

@Serializable
data class Motd(
    val msg: String,
    val url: String
)