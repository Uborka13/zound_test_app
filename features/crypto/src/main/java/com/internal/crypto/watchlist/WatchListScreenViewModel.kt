package com.internal.crypto.watchlist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internal.core.network.ui.ServerErrorHandler
import com.internal.core.network.ui.ServerErrorHandlerImpl
import com.internal.crypto.R
import com.internal.crypto.common.DetailedCryptosUIItem
import com.internal.crypto.dashboard.DashboardViewModel
import com.internal.repository.Repository
import com.internal.repository.model.Symbol
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class WatchListScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(), ServerErrorHandler by ServerErrorHandlerImpl() {

    val watchListState = mutableStateOf(WatchListUIItem())

    val currentCurrencyConversion = repository.currencyStateFlow

    private var cryptoJob: Job? = null

    private val cryptosList = repository.cryptosItemsListStateFlow.map { list ->
        list.map { model ->
            DetailedCryptosUIItem(
                name = model.baseAsset,
                symbol = model.symbol,
                quote = model.quoteAsset,
                askPrice = model.askPrice,
                bidPrice = model.bidPrice,
                lowPrice = model.lowPrice,
                highPrice = model.highPrice,
                openPrice = model.openPrice,
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
                watchListState.value = WatchListUIItem.error(it.httpStatusCode)
            },
            networkErrorHandler = {
                watchListState.value =
                    WatchListUIItem.error(R.string.lbl_internet_or_server_not_available)
            }
        ) {
            if (initialCall) watchListState.value = WatchListUIItem.loading()
            repository.getCryptos()
            delay(DashboardViewModel.REFRESH_RATE)
            getCryptos(false)
        }
    }

    fun forceRefreshCryptos() {
        cryptoJob?.cancel()
        cryptoJob = launch(
            viewModelScope,
            apiErrorHandler = {
                watchListState.value = WatchListUIItem.error(it.httpStatusCode.toString())
            },
            networkErrorHandler = {
                watchListState.value = WatchListUIItem.error("Internet or Server unavailable")
            }
        ) {
            watchListState.value = WatchListUIItem.loading()
            repository.forceRefreshCryptos()
            delay(DashboardViewModel.REFRESH_RATE)
            getCryptos(false)
        }
    }

    fun startCollectingWatchList() {
        launch(viewModelScope) {
            combine(
                repository.getWatchListSymbolFlow(),
                cryptosList
            ) { watchListSymbols, cryptoList ->
                watchListSymbols.map { symbol ->
                    cryptoList.filter { it.symbol == symbol }
                }
            }.collect { list ->
                watchListState.value = WatchListUIItem.success(list.flatten())
            }
        }
    }

    fun changeCurrency() {
        launch(viewModelScope) {
            repository.changeCurrency()
        }
    }

    fun removeFromWatchList(symbol: String) {
        launch(viewModelScope) {
            repository.removeFromWatchList(Symbol(symbol))
        }
    }
}
