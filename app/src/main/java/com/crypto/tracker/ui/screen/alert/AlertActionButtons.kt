package com.crypto.tracker.ui.screen.alert

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crypto.tracker.R
import com.crypto.tracker.ui.theme.CryptoTrackerTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AlertActionButtons(
    coinId: String,
    enabled: Boolean,
    onNavigateToAlertHistory: ((coinId: String) -> Unit)? = null,
    onCheckAlert: (() -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column {
        Button(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
            enabled = enabled,
            onClick = {
                keyboardController?.hide()
                onCheckAlert?.let { it() }
            }
        ) {
            Text(stringResource(id = R.string.check_button))
        }
        Button(
            modifier = Modifier.padding(8.dp),
            enabled = enabled,
            onClick = {
                keyboardController?.hide()
                onNavigateToAlertHistory?.let { it(coinId) }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        ) {
            Text(stringResource(id = R.string.history_button))
        }
    }
}

/**
 * Previews with fake sample data
 */
@Preview
@Composable
fun AlertActionButtonsPreviewLight() {
    CryptoTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AlertActionButtons(coinId = "bitcoin", enabled = true)
        }
    }
}

@Preview
@Composable
fun AlertActionButtonsPreviewDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            AlertActionButtons(coinId = "bitcoin", enabled = true)
        }
    }
}