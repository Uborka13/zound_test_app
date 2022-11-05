package com.internal.crypto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.internal.crypto.dashboard.DashboardScreen
import com.internal.crypto.detail.CryptoDetailsScreen
import com.internal.crypto.list.CryptosListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.DashboardScreen.route
    ) {
        composable(Routes.DashboardScreen.route) {
            DashboardScreen(
                onSeeAllClicked = {
                    navController.navigate(Routes.CryptoListScreen.route)
                }
            )
        }
        composable(Routes.CryptoListScreen.route) {
            CryptosListScreen(onBackPressed = { navController.navigateUp() })
        }
        composable(Routes.CryptoDetailsScreen.route) {
            CryptoDetailsScreen(onBackPressed = { navController.navigateUp() })
        }
    }
}
