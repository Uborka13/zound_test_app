package com.internal.repository

import com.internal.data.local.dao.WatchListDao
import com.internal.data.local.entities.WatchListEntity
import com.internal.data.remote.model.rates.Rates
import com.internal.data.remote.service.CryptoService
import com.internal.data.remote.service.RatesService
import com.internal.repository.mapper.RepositoryMapper
import com.internal.repository.model.Symbol
import com.internal.repository.model.crypto.CryptoRepoModel
import com.internal.repository.model.rates.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

internal class RepositoryImpl @Inject constructor(
    private val cryptoService: CryptoService,
    private val ratesService: RatesService,
    private val watchListDao: WatchListDao,
    private val mapper: RepositoryMapper
) : Repository {

    private var localCacheHelper: Long = 0L

    private val _currencyMutableStateFlow =
        MutableStateFlow(ConvertedCurrency(Currency.USD, Currency.SEK(1.0)))
    override val currencyStateFlow: StateFlow<ConvertedCurrency> = _currencyMutableStateFlow

    private val _cryptosItemsListStateFlow = MutableStateFlow(listOf<CryptoRepoModel>())
    override val cryptosItemsListStateFlow: StateFlow<List<CryptoRepoModel>> =
        _cryptosItemsListStateFlow

    override suspend fun getCryptos() {
        if (localCacheHelper < Instant.now().toEpochMilli()) {
            localCacheHelper = Instant.now().plusSeconds(BUFFER_TIME_IN_SEC).toEpochMilli()
            val response = cryptoService.getCryptos()
            _cryptosItemsListStateFlow.value = response.map { mapper.mapToRepoModel(it) }
        }
    }

    override suspend fun forceRefreshCryptos() {
        localCacheHelper = Instant.now().plusSeconds(BUFFER_TIME_IN_SEC).toEpochMilli()
        val response = cryptoService.getCryptos()
        _cryptosItemsListStateFlow.value = response.map { mapper.mapToRepoModel(it) }
    }

    override suspend fun changeCurrency() {
        val convertFrom = currencyStateFlow.value.current
        val convertTo = currencyStateFlow.value.other
        val result = ratesService.convertRates(
            from = getRatesFromConvertRates(convertFrom),
            to = getRatesFromConvertRates(convertTo)
        )
        when (convertTo) {
            is Currency.USD -> _currencyMutableStateFlow.emit(
                ConvertedCurrency(
                    Currency.USD,
                    Currency.SEK(1.0)
                )
            )
            is Currency.SEK -> _currencyMutableStateFlow.emit(
                ConvertedCurrency(
                    Currency.SEK(result.result),
                    Currency.USD
                )
            )
        }
    }

    override fun getWatchListSymbolFlow(): Flow<List<String>> {
        return watchListDao.getWatchList().map { list ->
            list.map { entity ->
                entity.symbol
            }
        }
    }

    override suspend fun addToWatchList(symbol: Symbol) {
        watchListDao.insertWatchListEntity(WatchListEntity(symbol.value, Instant.now().epochSecond))
    }

    override suspend fun removeFromWatchList(symbol: Symbol) {
        watchListDao.deleteWatchListEntity(symbol.value)
    }

    private fun getRatesFromConvertRates(currency: Currency): Rates {
        return when (currency) {
            is Currency.USD -> Rates.USD
            is Currency.SEK -> Rates.SEK
        }
    }

    companion object {
        // Need this buffer to prevent the api from being called too often and getting error 429
        private const val BUFFER_TIME_IN_SEC = 30L
    }
}

data class ConvertedCurrency(
    val current: Currency,
    val other: Currency
)
