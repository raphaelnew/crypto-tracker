package com.crypto.tracker.ui.screen.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crypto.tracker.R
import com.crypto.tracker.ui.theme.CryptoTrackerTheme

@Composable
fun HistoryHeaderUI() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            val modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            Text(
                text = stringResource(id = R.string.history_header_range),
                modifier = modifier.weight(0.4f),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(id = R.string.history_header_price),
                modifier = modifier.weight(0.3f),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(id = R.string.history_header_direction),
                modifier = modifier.weight(0.3f),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        })
}

/**
 * Previews with fake sample data
 */
@Preview
@Composable
fun HistoryHeaderUIPreviewLight() {
    CryptoTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            HistoryHeaderUI()
        }
    }
}

@Preview
@Composable
fun HistoryHeaderUIPreviewDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            HistoryHeaderUI()
        }
    }
}