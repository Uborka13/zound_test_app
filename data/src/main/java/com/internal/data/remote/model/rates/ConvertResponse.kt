package com.internal.data.remote.model.rates

@kotlinx.serialization.Serializable
data class ConvertResponse(
    val date: String,
    val historical: Boolean,
    val info: Info,
    val motd: Motd,
    val query: Query,
    val result: Double,
    val success: Boolean
)
