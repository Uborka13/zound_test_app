package com.internal.data.remote.model.rates

import kotlinx.serialization.Serializable

@Serializable
data class Query(
    val amount: Int,
    val from: String,
    val to: String
)