package com.internal.data.remote.model.crypto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoResponseItem(
    @SerialName("askPrice")
    val askPrice: String,
    @SerialName("at")
    val at: Long,
    @SerialName("baseAsset")
    val baseAsset: String,
    @SerialName("bidPrice")
    val bidPrice: String,
    @SerialName("highPrice")
    val highPrice: String,
    @SerialName("lastPrice")
    val lastPrice: String,
    @SerialName("lowPrice")
    val lowPrice: String,
    @SerialName("openPrice")
    val openPrice: String,
    @SerialName("quoteAsset")
    val quoteAsset: String,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("volume")
    val volume: String
)
