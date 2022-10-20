package com.crypto.tracker.ui.screen.alert

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crypto.tracker.R
import com.crypto.tracker.ui.theme.CryptoTrackerTheme
import com.crypto.tracker.ui.viewmodel.AlertInputUiState

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UserInput(
    inputUiState: AlertInputUiState,
    enabled: Boolean,
    onValidateMinInput: ((String) -> Unit)? = null,
    onValidateMaxInput: ((String) -> Unit)? = null,
    onCheckAlert: (() -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .weight(0.5f),
                value = inputUiState.min,
                onValueChange = { input ->
                    onValidateMinInput?.let { it(input) }
                },
                label = { Text(stringResource(id = R.string.min_hint)) },
                singleLine = true,
                enabled = enabled,
                isError = (inputUiState is AlertInputUiState.Error) && inputUiState.minMaxError != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    onCheckAlert?.let { it() }
                })
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .weight(0.5f),
                value = inputUiState.max,
                onValueChange = { input ->
                    onValidateMaxInput?.let { it(input) }
                },
                label = { Text(stringResource(id = R.string.max_hint)) },
                singleLine = true,
                enabled = enabled,
                isError = (inputUiState is AlertInputUiState.Error) && inputUiState.minMaxError != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    onCheckAlert?.let { it() }
                })
            )
        }
        if (inputUiState is AlertInputUiState.Error && inputUiState.minMaxError != null) {
            Text(
                text = inputUiState.minMaxError,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Previews with fake sample data
 */
@Preview
@Composable
fun UserInputPreviewLight() {
    CryptoTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            UserInput(AlertInputUiState.Normal("12333", "13444"), enabled = true)
        }
    }
}

@Preview
@Composable
fun UserInputPreviewDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            UserInput(AlertInputUiState.Normal("", ""), enabled = true)
        }
    }
}

@Preview
@Composable
fun UserInputErrorPreviewLight() {
    CryptoTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            UserInput(
                AlertInputUiState.Error(
                    "2",
                    "1",
                    stringResource(id = R.string.min_max_error)
                ),
                enabled = true
            )
        }
    }
}

@Preview
@Composable
fun UserInputErrorPreviewDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            UserInput(
                AlertInputUiState.Error(
                    "10",
                    "1",
                    stringResource(id = R.string.min_max_error)
                ),
                enabled = true
            )
        }
    }
}