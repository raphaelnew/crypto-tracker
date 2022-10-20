package com.crypto.tracker.ui.screen.alert

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crypto.tracker.R
import com.crypto.tracker.model.Coin
import com.crypto.tracker.model.CoinPrice
import com.crypto.tracker.ui.theme.CryptoTrackerTheme
import com.crypto.tracker.ui.viewmodel.AlertPriceUiState
import com.crypto.tracker.util.Constants

@Composable
fun CurrentPriceLabel(alertPriceUiState: AlertPriceUiState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (alertPriceUiState) {
            is AlertPriceUiState.Loaded -> {
                Text(
                    text = alertPriceUiState.coin.coinPrice?.getPriceFormatted(Constants.CURRENCIES)
                        ?.let { price ->
                            stringResource(
                                id = R.string.current_price,
                                alertPriceUiState.coin.coinId,
                                price
                            )
                        } ?: stringResource(id = R.string.no_current_price),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }
            is AlertPriceUiState.Error -> {
                Text(
                    text = stringResource(id = R.string.error_prefix, alertPriceUiState.message),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.error
                )
            }
            is AlertPriceUiState.Empty -> {
                Text(
                    text = stringResource(id = R.string.no_price),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }
            is AlertPriceUiState.Loading -> {
                CircularProgressIndicator(Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

/**
 * Previews with fake sample data
 */
@Preview
@Composable
fun CurrentPriceLabelLoadedPreviewLight() {
    CryptoTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CurrentPriceLabel(
                AlertPriceUiState.Loaded(
                    Coin(
                        coinId = "bitcoin",
                        coinPrice = CoinPrice(
                            mapOf(
                                "usd" to "1320.631",
                                "usd_24h_change" to "2.8448"
                            )
                        )
                    )
                )
            )
        }
    }
}

@Preview
@Composable
fun CurrentPriceLabelLoadedPreviewDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            CurrentPriceLabel(
                AlertPriceUiState.Loaded(
                    Coin(
                        coinId = "bitcoin",
                        coinPrice = CoinPrice(
                            mapOf(
                                "usd" to "1320.631",
                                "usd_24h_change" to "2.8448"
                            )
                        )
                    )
                )
            )
        }
    }
}

@Preview
@Composable
fun CurrentPriceLabelEmptyPreviewLight() {
    CryptoTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CurrentPriceLabel(AlertPriceUiState.Empty)
        }
    }
}

@Preview
@Composable
fun CurrentPriceLabelEmptyPreviewDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            CurrentPriceLabel(AlertPriceUiState.Empty)
        }
    }
}

@Preview
@Composable
fun CurrentPriceLabelErrorPreviewLight() {
    CryptoTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CurrentPriceLabel(AlertPriceUiState.Error("Error"))
        }
    }
}

@Preview
@Composable
fun CurrentPriceLabelErrorPreviewDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            CurrentPriceLabel(AlertPriceUiState.Error("Error"))
        }
    }
}

@Preview
@Composable
fun CurrentPriceLabelLoadingPreviewLight() {
    CryptoTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CurrentPriceLabel(AlertPriceUiState.Loading(false))
        }
    }
}

@Preview
@Composable
fun CurrentPriceLabelLoadingPreviewDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            CurrentPriceLabel(AlertPriceUiState.Loading(false))
        }
    }
}