package com.internal.data.remote.model.crypto

import kotlinx.serialization.Serializable

@Serializable
data class CryptoResponse(
    val data: List<CryptoResponseItem>
)
