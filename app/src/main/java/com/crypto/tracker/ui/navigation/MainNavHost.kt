package com.crypto.tracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.crypto.tracker.ui.screen.home.HomeScreen
import com.crypto.tracker.ui.viewmodel.HomeViewModel

/**
 * NavHost composable.
 */
@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = viewModel,
                onNavigateToAlert = { coinId ->
                    // todo navigate to alert screen.
                })
        }
        //todo alert screen and history screen.
    }
}