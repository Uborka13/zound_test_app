package com.internal.crypto.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internal.core.network.ui.ServerErrorHandler
import com.internal.core.network.ui.ServerErrorHandlerImpl
import com.internal.crypto.R
import com.internal.crypto.common.DetailedCryptosUIItem
import com.internal.repository.Repository
import com.internal.repository.model.Symbol
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CryptosListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(), ServerErrorHandler by ServerErrorHandlerImpl() {

    private var cryptoJob: Job? = null

    val cryptosListState = mutableStateOf(CryptosListUIItem())
    val addToWatchListState = mutableStateOf(WatchListItem())

    val currentCurrencyConversion = repository.currencyStateFlow

    val cryptosList = repository.cryptosItemsListStateFlow.map { list ->
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
                cryptosListState.value = CryptosListUIItem.error(it.httpStatusCode.toString())
            },
            networkErrorHandler = {
                cryptosListState.value =
                    CryptosListUIItem.error(R.string.lbl_internet_or_server_not_available)
            }
        ) {
            if (initialCall) cryptosListState.value = CryptosListUIItem.loading()
            repository.getCryptos()
            cryptosListState.value = CryptosListUIItem.success()
            delay(REFRESH_RATE)
            getCryptos(false)
        }
    }

    fun forceRefreshCryptos() {
        cryptoJob?.cancel()
        cryptoJob = launch(
            viewModelScope,
            apiErrorHandler = {
                cryptosListState.value = CryptosListUIItem.error(it.httpStatusCode.toString())
            },
            networkErrorHandler = {
                cryptosListState.value =
                    CryptosListUIItem.error(R.string.lbl_internet_or_server_not_available)
            }
        ) {
            cryptosListState.value = CryptosListUIItem.loading()
            repository.forceRefreshCryptos()
            cryptosListState.value = CryptosListUIItem.success()
            delay(REFRESH_RATE)
            getCryptos(false)
        }
    }

    fun changeCurrency() {
        launch(viewModelScope) {
            repository.changeCurrency()
        }
    }

    fun addToWatchList(item: DetailedCryptosUIItem) {
        launch(viewModelScope) {
            addToWatchListState.value = WatchListItem.loading()
            repository.addToWatchList(Symbol(item.symbol))
            addToWatchListState.value = WatchListItem.success(item.name.uppercase())
        }
    }

    companion object {
        const val REFRESH_RATE = 60_000L
    }
}
