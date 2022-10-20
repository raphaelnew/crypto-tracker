package com.crypto.tracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.crypto.tracker.ui.screen.alert.AlertScreen
import com.crypto.tracker.ui.screen.home.HomeScreen
import com.crypto.tracker.ui.viewmodel.AlertViewModel
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
                    navController.navigate("alert/$coinId")
                })
        }
        composable(
            "alert/{coinId}",
            arguments = listOf(navArgument("coinId") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel = hiltViewModel<AlertViewModel>()
            AlertScreen(
                coinId = backStackEntry.arguments?.getString("coinId"),
                viewModel = viewModel,
                onNavigateToAlertHistory = { coinId ->
                    //todo navigate to history screen.
                },
                onNavigateBack = { navController.navigateUp() })
        }
        //todo history screen.
    }
}