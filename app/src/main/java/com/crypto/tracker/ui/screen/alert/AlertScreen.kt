package com.crypto.tracker.ui.screen.alert

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.crypto.tracker.R
import com.crypto.tracker.model.Coin
import com.crypto.tracker.model.CoinPrice
import com.crypto.tracker.ui.theme.CryptoTrackerTheme
import com.crypto.tracker.ui.viewmodel.AlertInputUiState
import com.crypto.tracker.ui.viewmodel.AlertPriceUiState
import com.crypto.tracker.ui.viewmodel.AlertViewModel
import com.crypto.tracker.ui.viewmodel.UserInputCallbacks
import kotlinx.coroutines.launch
import java.util.*

/**
 * Screen for adding/removing Alert for selected Coin.
 */
@Composable
fun AlertScreen(
    coinId: String?,
    viewModel: AlertViewModel,
    onNavigateToAlertHistory: (coinId: String) -> Unit,
    onNavigateBack: () -> Unit
) {
    coinId ?: run {
        onNavigateBack()
        return
    }

    AlertScreen(
        coinId = coinId,
        priceState = viewModel.priceUiState.collectAsState().value,
        inputUiState = viewModel.inputUiState.collectAsState().value,
        onNavigateToAlertHistory = onNavigateToAlertHistory,
        callbacks = viewModel,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlertScreen(
    coinId: String,
    priceState: AlertPriceUiState,
    inputUiState: AlertInputUiState,
    callbacks: UserInputCallbacks? = null,
    onNavigateToAlertHistory: ((coinId: String) -> Unit)? = null,
    onNavigateBack: (() -> Unit)? = null
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.alert_screen_title, coinId)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack?.let { it() } }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Navigate back")
                    }
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CurrentPriceLabel(alertPriceUiState = priceState)
                    UserInput(
                        inputUiState = inputUiState,
                        enabled = (priceState !is AlertPriceUiState.Loading),
                        onValidateMinInput = { input ->
                            callbacks?.onValidateMinInput(input)
                        },
                        onValidateMaxInput = { input ->
                            callbacks?.onValidateMaxInput(input)
                        },
                        onCheckAlert = {
                            callbacks?.onCheckAlert { message ->
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(message)
                                }
                            }
                        })
                    AlertActionButtons(
                        coinId = coinId,
                        enabled = (priceState !is AlertPriceUiState.Loading),
                        onNavigateToAlertHistory = onNavigateToAlertHistory,
                        onCheckAlert = {
                            callbacks?.onCheckAlert { message ->
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(message)
                                }
                            }
                        }
                    )
                }

            }
        })
}

/**
 * Previews with fake sample data
 */
@Preview
@Composable
fun AlertPreviewLight() {
    CryptoTrackerTheme {
        AlertScreen(
            coinId = "bitcoin",
            priceState = AlertPriceUiState.Loaded(Coin("bitcoin", CoinPrice(mapOf()))),
            inputUiState = AlertInputUiState.Normal()
        )
    }
}

@Preview
@Composable
fun AlertPreviewDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        AlertScreen(
            coinId = "bitcoin",
            priceState = AlertPriceUiState.Loaded(Coin("bitcoin", CoinPrice(mapOf()))),
            inputUiState = AlertInputUiState.Normal()
        )
    }
}