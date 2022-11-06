package com.internal.crypto.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.internal.crypto.R
import com.internal.repository.model.rates.Currency

@Composable
fun MoonAppBar(
    @StringRes titleRes: Int,
    onBackPressed: () -> Unit,
    onCurrencyChangePressed: () -> Unit = {},
    currentCurrency: Currency = Currency.USD,
    otherCurrency: Currency = Currency.SEK()
) {
    TopAppBar(
        title = { Text(text = stringResource(titleRes)) },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = { onCurrencyChangePressed() }
            ) {
                Row {
                    Text(
                        text = currentCurrency.symbol,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Icon(
                        painterResource(id = R.drawable.ic_autorenew),
                        contentDescription = null
                    )
                    Text(
                        text = otherCurrency.symbol,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    )
}
