package com.crypto.tracker.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crypto.tracker.R
import com.crypto.tracker.model.Coin
import com.crypto.tracker.model.CoinPrice
import com.crypto.tracker.ui.theme.CryptoTrackerTheme
import com.crypto.tracker.util.Constants
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinItem(coin: Coin, onNavigateToAlert: ((coinId: String) -> Unit)? = null) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraSmall,
        onClick = { onNavigateToAlert?.let { it(coin.coinId) } }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            content = {
                val modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.33f)
                Text(
                    text = coin.coinId,
                    modifier = modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = coin.coinPrice?.getPriceFormatted(Constants.CURRENCIES)
                        ?: stringResource(id = R.string.no_price),
                    modifier = modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
                Price24hChangeLabel(
                    coin.coinPrice?.get24hrChangeOrNull(Constants.CURRENCIES),
                    modifier = modifier
                )
            })
    }
}

/**
 * Previews with fake sample data
 */
@Preview
@Composable
fun CoinItemPreviewLight() {
    CryptoTrackerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.wrapContentSize()
        ) {
            CoinItem(
                coin = Coin(
                    coinId = "bitcoin",
                    coinPrice = CoinPrice(mapOf("usd" to "1320.631", "usd_24h_change" to "2.8448"))
                )
            )
        }
    }
}

@Preview
@Composable
fun CoinItemPreviewDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.wrapContentSize()
        ) {
            CoinItem(
                coin = Coin(
                    coinId = "bitcoin",
                    coinPrice = CoinPrice(mapOf("usd" to "1320.631", "usd_24h_change" to "-2.8448"))
                )
            )
        }
    }
}