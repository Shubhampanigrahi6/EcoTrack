
package com.superhumans.ecotrack.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Community : Screen("community")
    data object Map : Screen("map")
    data object Leaderboard : Screen("leaderboard")
    data object Profile : Screen("profile")

    // Additional screens (not bottom navigation tabs)
    data object Missions : Screen("missions")
    data object Login : Screen("login")
    data object Signup : Screen("signup")
    data object Chat : Screen("chat")
    data object Reports : Screen("reports")
    data object Settings : Screen("settings")
}
