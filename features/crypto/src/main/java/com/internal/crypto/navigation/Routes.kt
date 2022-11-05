package com.internal.crypto.navigation

sealed class Routes(val route: String) {
    object DashboardScreen : Routes("dashboardScreen")
    object CryptoListScreen : Routes("cryptoListScreen")
    object CryptoDetailsScreen : Routes("cryptoDetailsScreen")
}
