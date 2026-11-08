package com.superhumans.ecotrack.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.superhumans.ecotrack.core.components.EcoTrackCard
import com.superhumans.ecotrack.core.components.EcoTrackTopBar
import com.superhumans.ecotrack.ui.theme.EcoBackground
import com.superhumans.ecotrack.ui.theme.EcoNavy

@Composable
private fun PlaceholderScreen(
    title: String,
    description: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        EcoTrackTopBar(title = title)

        EcoTrackCard {
            Text(
                text = description,
                color = EcoNavy
            )
        }
    }
}

@Composable
fun HomeScreen() {
    PlaceholderScreen(
        title = "Home",
        description = "Your sustainability journey starts here."
    )
}

@Composable
fun CommunityScreen() {
    PlaceholderScreen(
        title = "Community",
        description = "Connect with people making a difference."
    )
}

@Composable
fun MapScreen() {
    PlaceholderScreen(
        title = "Nearby Friends",
        description = "Find active friends around you."
    )
}

@Composable
fun LeaderboardScreen() {
    PlaceholderScreen(
        title = "Leaderboard",
        description = "See how your sustainability efforts rank."
    )
}

@Composable
fun ProfileScreen() {
    PlaceholderScreen(
        title = "Profile",
        description = "Manage your EcoTrack profile."
    )
}