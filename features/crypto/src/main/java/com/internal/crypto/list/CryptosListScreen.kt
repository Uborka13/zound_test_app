package com.internal.crypto.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.internal.crypto.R

@Composable
fun CryptosListScreen(
    viewModel: CryptosListViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getCryptos()
    }
    Scaffold(topBar = { CryptosListAppBar(viewModel, onBackPressed) }) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewModel.cryptosListState.value) { item ->
                Text(text = item.name)
            }
        }
    }
}

@Composable
fun CryptosListAppBar(viewModel: CryptosListViewModel, onBackPressed: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.lbl_crypto_list_screen_name)) },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        },
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
