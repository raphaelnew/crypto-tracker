package com.crypto.tracker.ui.screen.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.crypto.tracker.ui.viewmodel.HomeViewModel

/**
 * Home screen of app with List of Coin exchange rates.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToAlert: (coinId: String) -> Unit
) {
    Text(text = "Test")
    //todo home screen UI.
}