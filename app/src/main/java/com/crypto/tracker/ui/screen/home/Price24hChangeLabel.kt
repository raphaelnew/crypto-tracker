package com.crypto.tracker.ui.screen.home

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
import com.crypto.tracker.ui.theme.*

@Composable
fun Price24hChangeLabel(last24hChange: Double?, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = getChangeBgColor(last24hChange)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(
                id = getChangeIconResource(last24hChange)
            ),
            contentDescription = stringResource(id = R.string.last_24h_change_content_description),
            colorFilter = ColorFilter.tint(getChangeOnBgColor(last24hChange))
        )
        Text(
            text = stringResource(
                id = R.string.price_percentage_template,
                stringResource(
                    id = R.string.last_24h_change,
                    last24hChange?.toFloat() ?: 0.0f
                ),
                "%"
            ),
            color = getChangeOnBgColor(last24hChange),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun getChangeBgColor(last24hChange: Double?): Color {
    return when {
        last24hChange == null -> MaterialTheme.colorScheme.grey
        last24hChange < 0.0 -> MaterialTheme.colorScheme.red
        last24hChange > 0.0 -> MaterialTheme.colorScheme.green
        else -> MaterialTheme.colorScheme.grey
    }
}

@Composable
private fun getChangeOnBgColor(last24hChange: Double?): Color {
    return when {
        last24hChange == null -> MaterialTheme.colorScheme.onGrey
        last24hChange < 0.0 -> MaterialTheme.colorScheme.onRed
        last24hChange > 0.0 -> MaterialTheme.colorScheme.onGreen
        else -> MaterialTheme.colorScheme.onGrey
    }
}

private fun getChangeIconResource(last24hChange: Double?): Int {
    return when {
        last24hChange == null -> R.drawable.ic_baseline_neutral_24
        last24hChange < 0.0 -> R.drawable.ic_baseline_arrow_drop_down_24
        last24hChange > 0.0 -> R.drawable.ic_baseline_arrow_drop_up_24
        else -> R.drawable.ic_baseline_neutral_24
    }
}

@Preview
@Composable
fun Price24hChangeLabelPreviewEqualLight() {
    CryptoTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Price24hChangeLabel(null, Modifier.width(100.dp))
        }
    }
}

@Preview
@Composable
fun Price24hChangeLabelPreviewEqualDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Price24hChangeLabel(null, Modifier.width(100.dp))
        }
    }
}

@Preview
@Composable
fun Price24hChangeLabelPreviewPositiveLight() {
    CryptoTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Price24hChangeLabel(1.0, Modifier.width(100.dp))
        }
    }
}

@Preview
@Composable
fun Price24hChangeLabelPreviewPositiveDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Price24hChangeLabel(1.0, Modifier.width(100.dp))
        }
    }
}

@Preview
@Composable
fun Price24hChangeLabelPreviewNegativeLight() {
    CryptoTrackerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Price24hChangeLabel(-1.0, Modifier.width(100.dp))
        }
    }
}

@Preview
@Composable
fun Price24hChangeLabelPreviewNegativeDark() {
    CryptoTrackerTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Price24hChangeLabel(-1.0, Modifier.width(100.dp))
        }
    }
}