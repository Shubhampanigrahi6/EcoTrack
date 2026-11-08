package com.superhumans.ecotrack.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.superhumans.ecotrack.core.components.EcoBottomNavItem
import com.superhumans.ecotrack.core.components.EcoTrackBottomBar
import com.superhumans.ecotrack.presentation.community.feed.CommunityScreen
import com.superhumans.ecotrack.presentation.community.chat.ChatScreen
import com.superhumans.ecotrack.presentation.community.map.MapScreen
import com.superhumans.ecotrack.presentation.home.HomeScreen
import com.superhumans.ecotrack.presentation.leaderboard.LeaderboardScreen
import com.superhumans.ecotrack.presentation.profile.ProfileScreen
import com.superhumans.ecotrack.presentation.reports.ReportsScreen
import com.superhumans.ecotrack.presentation.settings.SettingsScreen
import com.superhumans.ecotrack.presentation.auth.login.LoginScreen
import com.superhumans.ecotrack.presentation.auth.signup.SignupScreen
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superhumans.ecotrack.presentation.auth.AuthViewModel
import com.superhumans.ecotrack.presentation.missions.MissionsScreen
import com.superhumans.ecotrack.presentation.home.EcoDataViewModel
import com.superhumans.ecotrack.presentation.missions.MissionsViewModel

@Composable
fun EcoTrackApp() {

    val navController = rememberNavController()
    val missionsViewModel: MissionsViewModel = viewModel()

    val ecoDataViewModel: EcoDataViewModel = viewModel()


    val authViewModel: AuthViewModel = viewModel()
    val authState by authViewModel.uiState.collectAsState()

    LaunchedEffect(authState.isAuthenticated) {
        if (authState.isAuthenticated) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    val navItems = listOf(
        EcoBottomNavItem("Home", Icons.Filled.Home, Screen.Home.route),
        EcoBottomNavItem("Community", Icons.Filled.Groups, Screen.Community.route),
        EcoBottomNavItem("Map", Icons.Filled.Map, Screen.Map.route),
        EcoBottomNavItem("Ranks", Icons.Filled.EmojiEvents, Screen.Leaderboard.route),
        EcoBottomNavItem("Profile", Icons.Filled.Person, Screen.Profile.route)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    val showBottomBar = currentRoute in navItems.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                EcoTrackBottomBar(
                    items = navItems,
                    selectedRoute = currentRoute,
                    onItemSelected = { route ->
                        navController.navigate(route) {
                            popUpTo(
                                navController.graph.findStartDestination().id
                            ) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {



            composable(Screen.Login.route) {
                LoginScreen(
                    onLogin = { email, password ->
                        authViewModel.login(email, password)
                    },
                    onSignUp = {
                        authViewModel.clearError()
                        navController.navigate(Screen.Signup.route)
                    },
                    isLoading = authState.isLoading,
                    errorMessage = authState.error
                )
            }

            composable(Screen.Signup.route) {
                SignupScreen(
                    onSignUp = { name, email, password ->
                        authViewModel.signup(name, email, password)
                    },
                    onLogin = {
                        authViewModel.clearError()
                        navController.popBackStack()
                    },
                    isLoading = authState.isLoading,
                    errorMessage = authState.error
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    ecoViewModel = ecoDataViewModel,
                    onOpenMissions = {
                        navController.navigate(Screen.Missions.route)
                    }
                )
            }
            composable(Screen.Missions.route) {
                MissionsScreen(
                    missionsViewModel = missionsViewModel,
                    ecoDataViewModel = ecoDataViewModel
                )
            }

            composable(Screen.Community.route) {
                CommunityScreen(
                    onOpenChat = {
                        navController.navigate(Screen.Chat.route)
                    }
                )
            }

            composable(Screen.Map.route) {
                MapScreen()
            }
            composable(Screen.Leaderboard.route) {
                LeaderboardScreen(ecoViewModel = ecoDataViewModel)
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    onOpenReports = {
                        navController.navigate(Screen.Reports.route)
                    },

                    onOpenSettings = {
                        navController.navigate(Screen.Settings.route)
                    }
                )
            }

            composable(Screen.Chat.route) {
                ChatScreen()
            }

            composable(Screen.Reports.route) {
                ReportsScreen(ecoViewModel = ecoDataViewModel)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    onLogout = {
                        // Clear saved login session
                        authViewModel.logout()

                        // Navigate to login and clear previous screens
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
