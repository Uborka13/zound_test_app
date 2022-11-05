package com.internal.crypto.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.internal.crypto.R
import com.internal.repository.model.rates.Currency
import java.util.Locale

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onSeeAllClicked: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getTrendingCryptos()
        viewModel.getWatchList()
    }
    Scaffold(topBar = {
        DashboardAppBar(viewModel)
    }) {
        Column {
            WatchList(viewModel)
            Top10List(viewModel, onSeeAllClicked)
        }
    }
}

@Composable
fun DashboardAppBar(viewModel: DashboardViewModel) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.lbl_dashboard_screen_name)) },
        actions = {
            IconButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = { viewModel.changeCurrency() }
            ) {
                Row {
                    Text(
                        text = viewModel.currentCurrencyConversion.collectAsState().value.current.symbol,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Icon(
                        painterResource(id = R.drawable.ic_autorenew),
                        contentDescription = null
                    )
                    Text(
                        text = viewModel.currentCurrencyConversion.collectAsState().value.other.symbol,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    )
}

@Composable
fun WatchList(viewModel: DashboardViewModel) {
    Column {
        Text(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            text = stringResource(R.string.lbl_watchlist_title)
        )
        val watchListStateValue = viewModel.watchListState.value
        when {
            watchListStateValue.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
                )
            }
            watchListStateValue.isError -> {
                Text(watchListStateValue.errorMsg)
                Button(onClick = { viewModel.getWatchList() }) {
                    Text(stringResource(R.string.lbl_retry))
                }
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
                    LazyRow(modifier = Modifier.fillMaxWidth()) {
                        items(viewModel.watchListState.value.uiItemList) { item ->
                            Text(text = item.name)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Top10List(viewModel: DashboardViewModel, onSeeAllClicked: () -> Unit) {
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
                    Text(highestGainersValue.errorMsg)
                    Button(onClick = { viewModel.getTrendingCryptos() }) {
                        Text(stringResource(R.string.lbl_retry))
                    }
                }
            }
            else -> {
                if (viewModel.top10HighestGainers.value.uiItemList.isEmpty()) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = stringResource(R.string.lbl_empty_list)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(viewModel.top10HighestGainers.value.uiItemList) { item ->
                            CryptoListItem(
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
fun CryptoListItem(item: CryptosUIItem, currentCurrency: Currency) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row {
            Column(modifier = Modifier.padding(8.dp)) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(text = item.name.uppercase())
                    Text("/")
                    Text(text = item.quote.uppercase(), fontSize = 12.sp)
                }
                PercentageText(item.percentage)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.End) {
                Row {
                    Text(text = currentCurrency.symbol)
                    Text(text = "${item.lastPrice.toDouble() * currentCurrency.rate}")
                }
                Row {
                    Text(stringResource(R.string.lbl_volume, item.volume))
                }
            }
        }
    }
}

@Composable
fun PercentageText(percentage: Float) {
    if (percentage >= 0) {
        Text(
            text = "+${String.format(Locale.getDefault(), "%.2f", percentage)}%",
            color = Color.Blue
        )
    } else {
        Text(
            text = "${String.format(Locale.getDefault(), "%.2f", percentage)}%",
            color = Color.Red
        )
    }
}
