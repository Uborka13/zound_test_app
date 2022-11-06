package com.internal.crypto.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.internal.crypto.R
import com.internal.repository.model.rates.Currency

@Composable
fun DetailedListItem(
    item: DetailedCryptosUIItem,
    currency: Currency,
    actionIcon: ImageVector? = null,
    onActionClicked: (() -> Unit)? = null
) {
    Card(shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Column {
                    NameAndQuoteText(name = item.name, quote = item.quote)
                    PercentageText(percentage = item.percentage)
                }
                actionIcon?.let {
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { onActionClicked?.invoke() }) {
                        Icon(actionIcon, contentDescription = null)
                    }
                }
            }
            PriceWithCurrencyAndTitleTextPair(
                titleStart = stringResource(R.string.lbl_open_price),
                titleEnd = stringResource(R.string.lbl_last_price),
                priceStart = item.openPrice,
                priceEnd = item.lastPrice,
                currency = currency
            )
            PriceWithCurrencyAndTitleTextPair(
                titleStart = stringResource(R.string.lbl_low_price),
                titleEnd = stringResource(R.string.lbl_high_price),
                priceStart = item.lowPrice,
                priceEnd = item.highPrice,
                currency = currency
            )
            PriceWithCurrencyAndTitleTextPair(
                titleStart = stringResource(R.string.lbl_ask_price),
                titleEnd = stringResource(R.string.lbl_bid_price),
                priceStart = item.askPrice,
                priceEnd = item.bidPrice,
                currency = currency
            )
        }
    }
}
