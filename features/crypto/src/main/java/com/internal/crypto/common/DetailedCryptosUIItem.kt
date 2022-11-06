package com.internal.crypto.common

data class DetailedCryptosUIItem(
    val name: String,
    val symbol: String,
    val quote: String,
    val askPrice: String,
    val bidPrice: String,
    val highPrice: String,
    val lowPrice: String,
    val openPrice: String,
    val lastPrice: String,
    val volume: String,
    val percentage: Float
)
