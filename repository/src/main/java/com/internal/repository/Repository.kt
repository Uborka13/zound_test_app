package com.internal.repository

import com.internal.repository.model.crypto.CryptoRepoModel
import com.internal.repository.model.rates.RateConverterRepoModel
import kotlinx.coroutines.flow.StateFlow

interface Repository {
    val currencyStateFlow: StateFlow<ConvertedCurrency>
    suspend fun getCryptos(): List<CryptoRepoModel>
    suspend fun getTop10TrendingCryptos(): List<CryptoRepoModel>
    suspend fun convertRate(model: RateConverterRepoModel)
}
