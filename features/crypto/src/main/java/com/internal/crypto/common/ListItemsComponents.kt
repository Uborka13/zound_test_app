package com.internal.crypto.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.internal.repository.model.rates.Currency
import java.util.Locale

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

@Composable
fun NameAndQuoteText(name: String, quote: String) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(text = name.uppercase())
        Text("/")
        Text(text = quote.uppercase(), fontSize = 12.sp)
    }
}

@Composable
fun PriceWithCurrencyAndTitleTextPair(
    titleStart: String,
    titleEnd: String,
    priceStart: String,
    priceEnd: String,
    currency: Currency
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row {
            Text(text = titleStart)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = titleEnd)
        }
        Row {
            PriceWithCurrencyText(price = priceStart, currency = currency)
            Spacer(modifier = Modifier.weight(1f))
            PriceWithCurrencyText(price = priceEnd, currency = currency)
        }
    }
}

@Composable
fun PriceWithCurrencyText(
    price: String,
    currency: Currency
) {
    Row {
        Text(text = currency.symbol)
        Text(text = "${price.toDouble() * currency.rate}")
    }
}
