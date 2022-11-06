package com.internal.crypto.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.internal.crypto.R
import com.internal.crypto.common.CryptosUIItem
import com.internal.crypto.common.NameAndQuoteText
import com.internal.crypto.common.PercentageText
import com.internal.crypto.common.PriceWithCurrencyText
import com.internal.repository.model.rates.Currency

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onWatchListClicked: () -> Unit,
    onSeeAllClicked: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getCryptos()
        viewModel.startCollectingWatchList()
        viewModel.startCollectingCryptosList()
    }
    Scaffold(topBar = {
        DashboardAppBar(viewModel)
    }) {
        Column {
            WatchList(viewModel, onWatchListClicked)
            Top10HighestGainers(viewModel, onSeeAllClicked)
        }
    }
}

@Composable
fun DashboardAppBar(viewModel: DashboardViewModel) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.lbl_dashboard_screen_name)) },
        actions = {
            val currencyConversion = viewModel.currentCurrencyConversion.collectAsState().value
            if (viewModel.currencyUIState.value.isLoading) {
                CircularProgressIndicator()
            } else {
                IconButton(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { viewModel.changeCurrency() }
                ) {
                    Row {
                        Text(
                            text = currencyConversion.current.symbol,
                            color = MaterialTheme.colors.onPrimary
                        )
                        Icon(
                            painterResource(id = R.drawable.ic_autorenew),
                            contentDescription = null
                        )
                        Text(
                            text = currencyConversion.other.symbol,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun WatchList(viewModel: DashboardViewModel, onWatchListClicked: () -> Unit) {
    Column {
        val watchListStateValue = viewModel.watchListState.value
        Row(modifier = Modifier.fillMaxWidth().align(Alignment.End)) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.lbl_watchlist_title)
            )
            Spacer(modifier = Modifier.weight(1f))
            if (watchListStateValue.uiItemList.isNotEmpty()) {
                TextButton(
                    modifier = Modifier.padding(end = 16.dp),
                    onClick = { onWatchListClicked() }
                ) {
                    Text(
                        text = stringResource(R.string.lbl_see_all),
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
        when {
            watchListStateValue.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
                )
            }
            watchListStateValue.isError -> {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = watchListStateValue.errorMsg
                        ?: watchListStateValue.errorMsgRes?.let { res ->
                            stringResource(
                                res
                            )
                        } ?: stringResource(R.string.lbl_something_went_wrong)
                )
            }
            else -> {
                if (viewModel.watchListState.value.uiItemList.isEmpty()) {
                    Card(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = MaterialTheme.colors.surface
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(R.string.lbl_empty_watchlist)
                        )
                    }
                } else {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(viewModel.watchListState.value.uiItemList) { item ->
                            WatchListItem(
                                item = item,
                                currentCurrency = viewModel.currentCurrencyConversion.collectAsState().value.current
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Top10HighestGainers(viewModel: DashboardViewModel, onSeeAllClicked: () -> Unit) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.End)
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.lbl_top_10_highest_gainers)
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(modifier = Modifier.padding(end = 16.dp), onClick = { onSeeAllClicked() }) {
                Text(
                    text = stringResource(R.string.lbl_see_all),
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
        val highestGainersValue = viewModel.top10HighestGainers.value
        when {
            highestGainersValue.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
                )
            }
            highestGainersValue.isError -> {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = highestGainersValue.errorMsg
                            ?: highestGainersValue.errorMsgRes?.let { res ->
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
                if (viewModel.top10HighestGainers.value.uiItemList.isEmpty()) {
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
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(viewModel.top10HighestGainers.value.uiItemList) { item ->
                            HighestGainerItem(
                                item = item,
                                viewModel.currentCurrencyConversion.collectAsState().value.current
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WatchListItem(item: CryptosUIItem, currentCurrency: Currency) {
    Card(
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            NameAndQuoteText(item.name, item.quote)
            PercentageText(percentage = item.percentage)
            PriceWithCurrencyText(item.lastPrice, currentCurrency)
        }
    }
}

@Composable
fun HighestGainerItem(item: CryptosUIItem, currentCurrency: Currency) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.padding(16.dp)) {
                NameAndQuoteText(item.name, item.quote)
                PercentageText(item.percentage)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.End
            ) {
                PriceWithCurrencyText(item.lastPrice, currentCurrency)
                Row {
                    Text(stringResource(R.string.lbl_volume, item.volume))
                }
            }
        }
    }
}
