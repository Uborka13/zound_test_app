package com.internal.crypto.watchlist

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.internal.crypto.R
import com.internal.crypto.common.DetailedListItem
import com.internal.crypto.common.MoonAppBar

@Composable
fun WatchListScreen(
    viewModel: WatchListScreenViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getCryptos()
        viewModel.startCollectingWatchList()
    }
    val currency = viewModel.currentCurrencyConversion.collectAsState().value
    Scaffold(topBar = {
        MoonAppBar(
            titleRes = R.string.lbl_watchlist_title,
            onBackPressed = { onBackPressed() },
            onCurrencyChangePressed = { viewModel.changeCurrency() },
            currentCurrency = currency.current,
            otherCurrency = currency.other
        )
    }) {
        Column(modifier = Modifier.fillMaxSize()) {
            val watchList = viewModel.watchListState.value
            when {
                watchList.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
                    )
                }
                watchList.isError -> {
                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = watchList.errorMsg ?: watchList.errorMsgRes?.let { res ->
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
                    if (watchList.uiItemList.isEmpty()) {
                        Column(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
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
                            items(watchList.uiItemList) { item ->
                                DetailedListItem(
                                    item = item,
                                    currency = currency.current,
                                    actionIcon = Icons.Default.Close
                                ) {
                                    viewModel.removeFromWatchList(item.symbol)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
