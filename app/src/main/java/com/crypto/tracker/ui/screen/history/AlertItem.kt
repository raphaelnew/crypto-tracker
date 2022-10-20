package com.crypto.tracker.ui.screen.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crypto.tracker.R
import com.crypto.tracker.model.Alert
import com.crypto.tracker.model.CoinPrice
import com.crypto.tracker.ui.theme.CryptoTrackerTheme
import com.crypto.tracker.util.Constants
import java.text.NumberFormat
import java.util.*

@Composable
fun AlertItem(alert: Alert) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            content = {
                val formattedMin = NumberFormat.getNumberInstance(Locale.getDefault())
                    .format(alert.min)
                    .toString()
                val formattedMax = NumberFormat.getNumberInstance(Locale.getDefault())
                    .format(alert.max)
                    .toString()
                Text(
                    text = stringResource(id = R.string.history_range, formattedMin, formattedMax),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.4f)
                        .padding(8.dp),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = alert.coinPrice?.getPriceFormatted(Constants.CURRENCIES)
                        ?: stringResource(id = R.string.no_price),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.3f)
                        .padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
                val modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
                DirectionChangeLabel(alert, modifier = modifier)
            })
    }
}

/**
 * Previews with fake sample data
 */
@Preview
@Composable
fun AlertItemPreviewLight() {
    CryptoTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AlertItem(
                Alert(
                    id = 0,
                    min = 1.0,
                    max = 112345.99,
                    coinId = "bitcoin",
                    coinPrice = CoinPrice(mapOf("usd" to "19205.3732631"))
                )
            )
        }
    }
}

@Preview
@Composable
fun AlertItemPreviewDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AlertItem(
                Alert(
                    id = 0,
                    min = 1.0,
                    max = 112345.99,
                    coinId = "bitcoin",
                    coinPrice = CoinPrice(mapOf("usd" to "19205.3732631"))
                )
            )
        }
    }
}