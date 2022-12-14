package com.internal.crypto.list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.internal.crypto.R
import com.internal.crypto.common.DetailedListItem
import com.internal.crypto.common.MoonAppBar

// TODO:
//  Add search bar
//  Add sorting options
//  Add filter

@Composable
fun CryptosListScreen(
    viewModel: CryptosListViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getCryptos()
    }
    val currency = viewModel.currentCurrencyConversion.collectAsState()
    val cryptosListItem = viewModel.cryptosList.collectAsState(emptyList())
    val cryptoListState = viewModel.cryptosListState.value
    val addToWatchlistStateValue = viewModel.addToWatchListState.value
    addToWatchlistStateValue.symbol?.let { symbol ->
        if (!addToWatchlistStateValue.isLoading) {
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.lbl_added_to_watchlist, symbol),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    Scaffold(topBar = {
        MoonAppBar(
            titleRes = R.string.lbl_crypto_list_screen_name,
            onBackPressed = { onBackPressed() },
            onCurrencyChangePressed = { viewModel.changeCurrency() },
            currentCurrency = currency.value.current,
            otherCurrency = currency.value.other
        )
    }) {
        Column {
            when {
                cryptoListState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
                cryptoListState.isError -> {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = cryptoListState.errorMsg
                                ?: cryptoListState.errorMsgRes?.let { res ->
                                    stringResource(
                                        res
                                    )
                                } ?: stringResource(R.string.lbl_something_went_wrong)
                        )
                        Button(onClick = { viewModel.forceRefreshCryptos() }) {
                            Text(stringResource(R.string.lbl_retry))
                        }
                    }
                }
                else -> {
                    if (cryptosListItem.value.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = stringResource(R.string.lbl_empty_list)
                            )
                            Button(onClick = { viewModel.forceRefreshCryptos() }) {
                                Text(stringResource(R.string.lbl_retry))
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(cryptosListItem.value) { item ->
                                DetailedListItem(
                                    item = item,
                                    currency = currency.value.current,
                                    actionIcon = Icons.Default.Add
                                ) {
                                    viewModel.addToWatchList(item)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
