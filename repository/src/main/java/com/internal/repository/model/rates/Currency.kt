package com.internal.repository.model.rates

sealed class Currency(open val rate: Double, open val symbol: String) {
    object USD : Currency(1.0, "$")
    data class SEK(override val rate: Double = 1.0) : Currency(rate, "kr")
}
