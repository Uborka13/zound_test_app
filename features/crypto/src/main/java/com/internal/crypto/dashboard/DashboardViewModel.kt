package com.internal.crypto.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internal.core.network.ui.ServerErrorHandler
import com.internal.core.network.ui.ServerErrorHandlerImpl
import com.internal.crypto.R
import com.internal.crypto.common.CryptosUIItem
import com.internal.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(), ServerErrorHandler by ServerErrorHandlerImpl() {

    private var cryptoJob: Job? = null

    val watchListState = mutableStateOf(DashboardUIItem())
    val top10HighestGainers = mutableStateOf(DashboardUIItem())
    val currencyUIState = mutableStateOf(CurrencyUIItem())

    val currentCurrencyConversion = repository.currencyStateFlow

    private val cryptosList = repository.cryptosItemsListStateFlow.map { list ->
        list.map { model ->
            CryptosUIItem(
                name = model.baseAsset,
                symbol = model.symbol,
                quote = model.quoteAsset,
                lastPrice = model.lastPrice,
                volume = model.volume,
                percentage = model.percentage
            )
        }
    }

    fun getCryptos(initialCall: Boolean = true) {
        cryptoJob?.cancel()
        cryptoJob = launch(
            viewModelScope,
            apiErrorHandler = {
                // TODO Proper error msg based on status code and error body
                watchListState.value = DashboardUIItem.error(it.httpStatusCode.toString())
                top10HighestGainers.value = DashboardUIItem.error(it.httpStatusCode.toString())
            },
            networkErrorHandler = {
                watchListState.value =
                    DashboardUIItem.error(R.string.lbl_internet_or_server_not_available)
                top10HighestGainers.value =
                    DashboardUIItem.error(R.string.lbl_internet_or_server_not_available)
            }
        ) {
            if (initialCall) listsAreLoading()
            repository.getCryptos()
            delay(REFRESH_RATE)
            getCryptos(false)
        }
    }

    fun forceRefreshCryptos() {
        cryptoJob?.cancel()
        cryptoJob = launch(viewModelScope, apiErrorHandler = {
            // TODO Proper error msg based on status code and error body
            watchListState.value = DashboardUIItem.error(it.httpStatusCode.toString())
            top10HighestGainers.value = DashboardUIItem.error(it.httpStatusCode.toString())
        }, networkErrorHandler = {
                watchListState.value =
                    DashboardUIItem.error(R.string.lbl_internet_or_server_not_available)
                top10HighestGainers.value =
                    DashboardUIItem.error(R.string.lbl_internet_or_server_not_available)
            }) {
            listsAreLoading()
            repository.forceRefreshCryptos()
            delay(REFRESH_RATE)
            getCryptos(false)
        }
    }

    private fun listsAreLoading() {
        watchListState.value = DashboardUIItem.loading()
        top10HighestGainers.value = DashboardUIItem.loading()
    }

    private suspend fun updateHighestGainers() {
        val highestGainers = cryptosList.first().sortedByDescending { it.percentage }.take(TOP_10)
        top10HighestGainers.value = DashboardUIItem.success(highestGainers)
    }

    fun startCollectingCryptosList() {
        launch(viewModelScope) {
            cryptosList.collect {
                updateHighestGainers()
            }
        }
    }

    fun startCollectingWatchList() {
        launch(viewModelScope) {
            combine(
                repository.getWatchListSymbolFlow(),
                repository.cryptosItemsListStateFlow
            ) { watchListSymbols, cryptoList ->
                watchListState.value = DashboardUIItem.loading()
                watchListSymbols.map { symbol ->
                    cryptoList.map { model ->
                        CryptosUIItem(
                            name = model.baseAsset,
                            symbol = model.symbol,
                            quote = model.quoteAsset,
                            lastPrice = model.lastPrice,
                            volume = model.volume,
                            percentage = model.percentage
                        )
                    }.filter { it.symbol == symbol }
                }
            }.collect { list ->
                watchListState.value = DashboardUIItem.success(list.flatten())
            }
        }
    }

    fun changeCurrency() {
        launch(viewModelScope, apiErrorHandler = {}, networkErrorHandler = {}) {
            currencyUIState.value = CurrencyUIItem.loading()
            repository.changeCurrency()
            currencyUIState.value = CurrencyUIItem.success()
        }
    }

    companion object {
        const val REFRESH_RATE = 60_000L
        const val TOP_10 = 10
    }
}
