package com.internal.crypto.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internal.core.network.ui.ServerErrorHandler
import com.internal.core.network.ui.ServerErrorHandlerImpl
import com.internal.repository.Repository
import com.internal.repository.model.rates.ConvertRates
import com.internal.repository.model.rates.Currency
import com.internal.repository.model.rates.RateConverterRepoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(), ServerErrorHandler by ServerErrorHandlerImpl() {

    val watchListState = mutableStateOf(DashboardUIItem())
    val top10HighestGainers = mutableStateOf(DashboardUIItem())

    val currentCurrencyConversion = repository.currencyStateFlow

    private var cryptoJob: Job? = null

    fun getTrendingCryptos(initialCall: Boolean = true) {
        cryptoJob?.cancel()
        cryptoJob = launch(
            viewModelScope,
            apiErrorHandler = {
                top10HighestGainers.value =
                    DashboardUIItem.error(it.httpStatusCode.toString()) // TODO Proper error msg based on status code and error body
            },
            networkErrorHandler = {
                top10HighestGainers.value =
                    DashboardUIItem.error("Internet or Server unavailable")
            }
        ) {
            if (initialCall) top10HighestGainers.value = DashboardUIItem.loading()
            val result = repository.getTop10TrendingCryptos()
            top10HighestGainers.value = DashboardUIItem.success(
                uiItemList = result.map { model ->
                    CryptosUIItem(
                        name = model.baseAsset,
                        quote = model.quoteAsset,
                        lastPrice = model.lastPrice,
                        volume = model.volume,
                        percentage = model.percentage
                    )
                }
            )
            delay(REFRESH_RATE)
            getTrendingCryptos(false)
        }
    }

    fun changeCurrency() {
        when (repository.currencyStateFlow.value.current) {
            is Currency.SEK -> changeCurrency(ConvertRates.SEK, ConvertRates.USD)
            is Currency.USD -> changeCurrency(ConvertRates.USD, ConvertRates.SEK)
        }
    }

    private fun changeCurrency(from: ConvertRates, to: ConvertRates) {
        launch(
            viewModelScope
        ) {
            repository.convertRate(
                RateConverterRepoModel(
                    from,
                    to
                )
            )
        }
    }

    fun getWatchList() {
        launch(viewModelScope) {
            watchListState.value = DashboardUIItem.loading()
            watchListState.value = DashboardUIItem.success(emptyList())
        }
    }

    companion object {
        const val REFRESH_RATE = 60_000L
    }
}
