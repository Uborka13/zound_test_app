package com.internal.repository

import com.internal.data.remote.model.rates.Rates
import com.internal.data.remote.service.CryptoService
import com.internal.data.remote.service.RatesService
import com.internal.repository.mapper.RepositoryMapper
import com.internal.repository.model.crypto.CryptoRepoModel
import com.internal.repository.model.rates.ConvertRates
import com.internal.repository.model.rates.Currency
import com.internal.repository.model.rates.RateConverterRepoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

internal class RepositoryImpl @Inject constructor(
    private val cryptoService: CryptoService,
    private val ratesService: RatesService,
    private val mapper: RepositoryMapper
) : Repository {

    private val _currencyMutableStateFlow =
        MutableStateFlow(ConvertedCurrency(Currency.USD, Currency.SEK(1.0)))
    override val currencyStateFlow: StateFlow<ConvertedCurrency> = _currencyMutableStateFlow

    override suspend fun getCryptos(): List<CryptoRepoModel> {
        val response = cryptoService.getCryptos()
        return response.map { cryptoItem ->
            mapper.mapToRepoModel(cryptoItem)
        }
    }

    override suspend fun getTop10TrendingCryptos(): List<CryptoRepoModel> {
        val response = cryptoService.getCryptos()
        return response.map { cryptoItem ->
            mapper.mapToRepoModel(cryptoItem)
        }.sortedByDescending { item -> item.percentage }.take(10)
    }

    override suspend fun convertRate(model: RateConverterRepoModel) {
        val result = ratesService.convertRates(
            from = getRatesFromConvertRates(model.from),
            to = getRatesFromConvertRates(model.to),
            amount = 1.0 // TODO: Keep the possibility to convert any amount
        )
        when (model.to) {
            ConvertRates.USD -> _currencyMutableStateFlow.emit(
                ConvertedCurrency(
                    Currency.USD,
                    Currency.SEK(1.0)
                )
            )
            ConvertRates.SEK -> _currencyMutableStateFlow.emit(
                ConvertedCurrency(
                    Currency.SEK(result.result),
                    Currency.USD
                )
            )
        }
    }

    private fun getRatesFromConvertRates(convertRates: ConvertRates): Rates {
        return when (convertRates) {
            ConvertRates.USD -> Rates.USD
            ConvertRates.SEK -> Rates.SEK
        }
    }
}

data class ConvertedCurrency(
    val current: Currency,
    val other: Currency
)
