package com.internal.repository

import com.internal.repository.model.Symbol
import com.internal.repository.model.crypto.CryptoRepoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Repository {
    val currencyStateFlow: StateFlow<ConvertedCurrency>
    val cryptosItemsListStateFlow: StateFlow<List<CryptoRepoModel>>
    suspend fun getCryptos()
    suspend fun forceRefreshCryptos()
    suspend fun changeCurrency()
    fun getWatchListSymbolFlow(): Flow<List<String>>
    suspend fun addToWatchList(symbol: Symbol)
    suspend fun removeFromWatchList(symbol: Symbol)
}
