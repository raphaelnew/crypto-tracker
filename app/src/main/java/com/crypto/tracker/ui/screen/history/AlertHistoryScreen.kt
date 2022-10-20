package com.crypto.tracker.ui.screen.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crypto.tracker.R
import com.crypto.tracker.model.Alert
import com.crypto.tracker.model.CoinPrice
import com.crypto.tracker.ui.theme.CryptoTrackerTheme
import com.crypto.tracker.ui.viewmodel.AlertHistoryState
import com.crypto.tracker.ui.viewmodel.AlertHistoryViewModel
import java.util.*

/**
 * Screen with list of alerts triggered for selected Coin.
 */
@Composable
fun AlertHistoryScreen(
    coinId: String?,
    viewModel: AlertHistoryViewModel,
    onNavigateBack: () -> Unit
) {
    coinId ?: run {
        onNavigateBack()
        return
    }

    AlertHistoryScreen(
        coinId = coinId,
        state = viewModel.uiState.collectAsState().value,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlertHistoryScreen(
    coinId: String,
    state: AlertHistoryState,
    onNavigateBack: (() -> Unit)? = null
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(text = stringResource(id = R.string.alert_history_screen_title, coinId))
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
            navigationIcon = {
                IconButton(onClick = { onNavigateBack?.let { it() } }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Navigate back")
                }
            }
        )
    }, content = { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state is AlertHistoryState.Loaded) {
                HistoryHeaderUI()
            }
            when (state) {
                is AlertHistoryState.Empty -> {
                    EmptyUI()
                }
                is AlertHistoryState.Loaded -> {
                    AlertList(state.data)
                }
            }

        }
    })
}

@Composable
fun AlertList(data: List<Alert>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = data,
            key = { alert ->
                // Return a stable + unique key for the item
                alert.id
            }
        ) { alert ->
            AlertItem(alert)
        }
    }
}

@Composable
fun EmptyUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.empty),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .wrapContentSize(),
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Previews with fake sample data
 */
@Preview
@Composable
fun AlertHistoryPreviewLight() {
    CryptoTrackerTheme {
        AlertHistoryScreen(
            coinId = "bitcoin", state = AlertHistoryState.Loaded(
                data = listOf(
                    Alert(
                        id = 0,
                        min = 1.0,
                        max = 112345.99,
                        coinId = "bitcoin",
                        coinPrice = CoinPrice(mapOf("usd" to "19205.3732631"))
                    ),
                    Alert(
                        id = 1,
                        min = 2.0,
                        max = 123456.9999,
                        coinId = "bitcoin",
                        coinPrice = CoinPrice(mapOf("usd" to "19205.3732631"))
                    ),
                    Alert(
                        id = 2,
                        min = 22000.0,
                        max = 112345.99,
                        coinId = "bitcoin",
                        coinPrice = CoinPrice(mapOf("usd" to "19205.3732631"))
                    )
                )
            )
        )
    }
}

@Preview
@Composable
fun AlertHistoryPreviewDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        AlertHistoryScreen(
            coinId = "bitcoin", state = AlertHistoryState.Loaded(
                data = listOf(
                    Alert(
                        id = 0,
                        min = 1.0,
                        max = 112345.99,
                        coinId = "bitcoin",
                        coinPrice = CoinPrice(mapOf("usd" to "19205.3732631"))
                    ),
                    Alert(
                        id = 1,
                        min = 2.0,
                        max = 123456.9999,
                        coinId = "bitcoin",
                        coinPrice = CoinPrice(mapOf("usd" to "19205.3732631"))
                    ),
                    Alert(
                        id = 2,
                        min = 22000.0,
                        max = 112345.99,
                        coinId = "bitcoin",
                        coinPrice = CoinPrice(mapOf("usd" to "19205.3732631"))
                    )
                )
            )
        )
    }
}

@Preview
@Composable
fun AlertHistoryEmptyPreviewLight() {
    CryptoTrackerTheme {
        AlertHistoryScreen(
            coinId = "bitcoin",
            state = AlertHistoryState.Empty
        )
    }
}

@Preview
@Composable
fun AlertHistoryEmptyPreviewDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        AlertHistoryScreen(
            coinId = "bitcoin",
            state = AlertHistoryState.Empty
        )
    }
}