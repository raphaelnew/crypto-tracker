package com.crypto.tracker.ui.screen.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crypto.tracker.R
import com.crypto.tracker.model.Alert
import com.crypto.tracker.model.CoinPrice
import com.crypto.tracker.ui.theme.*

@Composable
fun DirectionChangeLabel(alert: Alert, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = getChangeBgColor(alert.isPriceLowerThanRange)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(
                id = getChangeIconResource(alert.isPriceLowerThanRange)
            ),
            contentDescription = stringResource(id = R.string.alert_direction_content_description),
            colorFilter = ColorFilter.tint(getChangeOnBgColor(alert.isPriceLowerThanRange))
        )
        Text(
            text = stringResource(
                id = if (alert.isPriceLowerThanRange) {
                    R.string.alert_direction_low
                } else {
                    R.string.alert_direction_high
                }
            ),
            color = getChangeOnBgColor(alert.isPriceLowerThanRange),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun getChangeBgColor(isPriceLowerThanRange: Boolean): Color {
    return if (isPriceLowerThanRange) {
        MaterialTheme.colorScheme.red
    } else {
        MaterialTheme.colorScheme.green
    }
}

@Composable
private fun getChangeOnBgColor(isPriceLowerThanRange: Boolean): Color {
    return if (isPriceLowerThanRange) {
        MaterialTheme.colorScheme.onRed
    } else {
        MaterialTheme.colorScheme.onGreen
    }
}

@Composable
private fun getChangeIconResource(isPriceLowerThanRange: Boolean): Int {
    return if (isPriceLowerThanRange) {
        R.drawable.ic_baseline_arrow_drop_down_24
    } else {
        R.drawable.ic_baseline_arrow_drop_up_24
    }
}

/**
 * Previews with fake sample data
 */
@Preview
@Composable
fun DirectionChangeLabelPositivePreviewLight() {
    CryptoTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            DirectionChangeLabel(
                Alert(
                    id = 0,
                    min = 1.0,
                    max = 112345.99,
                    coinId = "bitcoin",
                    coinPrice = CoinPrice(mapOf("usd" to "19205.3732631"))
                ), modifier = Modifier.width(120.dp)
            )
        }
    }
}

@Preview
@Composable
fun DirectionChangeLabelPositivePreviewDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            DirectionChangeLabel(
                Alert(
                    id = 0,
                    min = 1.0,
                    max = 112345.99,
                    coinId = "bitcoin",
                    coinPrice = CoinPrice(mapOf("usd" to "19205.3732631"))
                ), modifier = Modifier.width(120.dp)
            )
        }
    }
}

@Preview
@Composable
fun DirectionChangeLabelNegativePreviewLight() {
    CryptoTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            DirectionChangeLabel(
                Alert(
                    id = 0,
                    min = 20000.0,
                    max = 25000.99,
                    coinId = "bitcoin",
                    coinPrice = CoinPrice(mapOf("usd" to "19205.3732631"))
                ), modifier = Modifier.width(120.dp)
            )
        }
    }
}

@Preview
@Composable
fun DirectionChangeLabelNegativePreviewDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            DirectionChangeLabel(
                Alert(
                    id = 0,
                    min = 20000.0,
                    max = 25000.99,
                    coinId = "bitcoin",
                    coinPrice = CoinPrice(mapOf("usd" to "19205.3732631"))
                ), modifier = Modifier.width(120.dp)
            )
        }
    }
}