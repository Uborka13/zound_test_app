package com.internal.crypto.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internal.core.network.ui.ServerErrorHandler
import com.internal.core.network.ui.ServerErrorHandlerImpl
import com.internal.crypto.dashboard.CryptosUIItem
import com.internal.repository.Repository
import com.internal.repository.model.rates.ConvertRates
import com.internal.repository.model.rates.Currency
import com.internal.repository.model.rates.RateConverterRepoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptosListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(), ServerErrorHandler by ServerErrorHandlerImpl() {

    val cryptosListState = mutableStateOf(listOf<CryptosUIItem>())

    val currentCurrencyConversion = repository.currencyStateFlow

    fun getCryptos() {
        launch(viewModelScope) {
            val result = repository.getCryptos()
            cryptosListState.value = result.map { model ->
                CryptosUIItem(
                    name = model.baseAsset,
                    quote = model.quoteAsset,
                    lastPrice = model.lastPrice,
                    volume = model.volume,
                    percentage = model.percentage
                )
            }
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
}
