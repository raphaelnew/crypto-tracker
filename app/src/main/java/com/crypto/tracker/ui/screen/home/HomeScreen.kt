package com.crypto.tracker.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crypto.tracker.R
import com.crypto.tracker.model.CoinPrices
import com.crypto.tracker.ui.theme.CryptoTrackerTheme
import com.crypto.tracker.ui.viewmodel.HomeUiState
import com.crypto.tracker.ui.viewmodel.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.*

/**
 * Home screen of app with List of Coin exchange rates.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToAlert: (coinId: String) -> Unit
) {
    HomeScreen(
        state = viewModel.uiState.collectAsState().value,
        onNavigateToAlert = onNavigateToAlert,
        onRefreshData = {
            viewModel.refresh()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    state: HomeUiState,
    onNavigateToAlert: ((coinId: String) -> Unit)? = null,
    onRefreshData: (() -> Unit)? = null,
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
        )
    }, content = { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state is HomeUiState.Loaded) {
                HomeHeaderUI()
            }
            val forceRefreshing = (state is HomeUiState.Loading) && state.forceRefresh
            SwipeRefresh(
                state = rememberSwipeRefreshState(forceRefreshing),
                onRefresh = { onRefreshData?.let { it() } },
            ) {
                when (state) {
                    is HomeUiState.Empty -> {
                        EmptyUI()
                    }
                    is HomeUiState.Error -> {
                        ErrorUI(state.message)
                    }
                    is HomeUiState.Loading -> {
                        LoadingUI()
                    }
                    is HomeUiState.Loaded -> {
                        CoinList(state.data, onNavigateToAlert)
                    }
                }
            }
        }
    })
}

@Composable
fun CoinList(
    coinPrices: CoinPrices,
    onNavigateToAlert: ((coinId: String) -> Unit)?
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = coinPrices.getCoins(),
            key = { coinPriceItem ->
                // Return a stable + unique key for the item
                coinPriceItem.coinId
            }
        ) { coinPriceItem ->
            CoinItem(coinPriceItem, onNavigateToAlert)
        }
    }
}

@Composable
fun LoadingUI() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.loading),
            modifier = Modifier.wrapContentSize()
        )
        CircularProgressIndicator(Modifier.padding(16.dp))
    }
}

@Composable
fun ErrorUI(message: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.error_prefix, message),
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .wrapContentSize(),
            textAlign = TextAlign.Center
        )
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
fun HomePreviewLoadedLight() {
    CryptoTrackerTheme {
        HomeScreen(state = HomeUiState.Loaded(
            data = CoinPrices(
                mapOf(
                    "bitcoin" to mapOf(
                        "usd" to "19205.3732631",
                        "usd_24h_change" to "-1.68448"
                    ),
                    "ethereum" to mapOf("usd" to "1320.631", "usd_24h_change" to "2.8448"),
                    "ripple" to mapOf("usd" to "0.3631", "usd_24h_change" to "-0.68448")
                )
            )
        ), onNavigateToAlert = {}, onRefreshData = {})
    }
}

@Preview
@Composable
fun HomePreviewLoadedDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        HomeScreen(
            state = HomeUiState.Loaded(
                data = CoinPrices(
                    mapOf(
                        "bitcoin" to mapOf(
                            "usd" to "19205.3732631",
                            "usd_24h_change" to "-1.68448"
                        ),
                        "ethereum" to mapOf("usd" to "1320.631", "usd_24h_change" to "2.8448"),
                        "ripple" to mapOf("usd" to "0.3631", "usd_24h_change" to "-0.68448")
                    )
                )
            )
        )
    }
}

@Preview
@Composable
fun HomePreviewEmptyLight() {
    CryptoTrackerTheme {
        HomeScreen(state = HomeUiState.Empty)
    }
}

@Preview
@Composable
fun HomePreviewEmptyDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        HomeScreen(state = HomeUiState.Empty)
    }
}

@Preview
@Composable
fun HomePreviewErrorLight() {
    CryptoTrackerTheme {
        HomeScreen(state = HomeUiState.Error("Error"))
    }
}

@Preview
@Composable
fun HomePreviewErrorDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        HomeScreen(state = HomeUiState.Error("Error"))
    }
}

@Preview
@Composable
fun HomePreviewLoadingLight() {
    CryptoTrackerTheme {
        HomeScreen(state = HomeUiState.Loading(false))
    }
}

@Preview
@Composable
fun HomePreviewLoadingDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        HomeScreen(state = HomeUiState.Loading(false))
    }
}