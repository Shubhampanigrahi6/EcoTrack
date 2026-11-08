package com.superhumans.ecotrack.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superhumans.ecotrack.core.components.EcoTrackCard
import com.superhumans.ecotrack.core.components.EcoTrackProgress
import com.superhumans.ecotrack.ui.theme.EcoBackground
import com.superhumans.ecotrack.ui.theme.EcoBlue
import com.superhumans.ecotrack.ui.theme.EcoCoral
import com.superhumans.ecotrack.ui.theme.EcoGradientEnd
import com.superhumans.ecotrack.ui.theme.EcoGradientStart
import com.superhumans.ecotrack.ui.theme.EcoNavy
import com.superhumans.ecotrack.ui.theme.EcoSurfaceBlue
import com.superhumans.ecotrack.ui.theme.EcoTextSecondary
import com.superhumans.ecotrack.ui.theme.EcoWhite

@Composable
fun HomeScreen(
    ecoViewModel: EcoDataViewModel = viewModel(),
    onOpenMissions: () -> Unit = {}

) {
    val uiState by ecoViewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = EcoBackground
    ) {
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = EcoBlue)
                }
            }

            uiState.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = uiState.error ?: "Unable to load data",
                            color = EcoNavy
                        )
                        Text(
                            text = "Please try again.",
                            color = EcoBlue,
                            modifier = Modifier.padding(8.dp)
                        )
                        androidx.compose.material3.TextButton(
                            onClick = { ecoViewModel.loadEcoData() }
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }

            uiState.data != null -> {
                val ecoData = uiState.data!!

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        horizontal = 20.dp,
                        vertical = 20.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    item {
                        HomeHeader(userName = ecoData.user.name)
                    }

                    item {
                        ImpactCard(
                            co2Saved = ecoData.impact.co2Saved,
                            monthlyGoal = ecoData.impact.monthlyGoal
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            StatCard(
                                modifier = Modifier.weight(1f),
                                title = "Eco Points",
                                value = ecoData.user.ecoPoints.toString(),
                                subtitle = "Your green rewards",
                                icon = Icons.Default.EmojiEvents,
                                iconColor = EcoBlue
                            )

                            StatCard(
                                modifier = Modifier.weight(1f),
                                title = "Day Streak",
                                value = "${ecoData.user.streak} Days",
                                subtitle = "Keep it going!",
                                icon = Icons.Default.LocalFireDepartment,
                                iconColor = EcoCoral
                            )
                        }
                    }

                    item {
                        WeeklyActivityCard(
                            weeklySavings = ecoData.impact.weeklySavings
                        )
                    }

                    item {
                        DailyChallengeCard(
                            onOpenMissions = onOpenMissions
                        )
                    }
                }
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No data available", color = EcoTextSecondary)
                }
            }
        }
    }
}

@Composable
private fun HomeHeader(userName: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = "Good morning, $userName 👋",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = EcoNavy
            )

            Text(
                text = "Let's make today greener.",
                fontSize = 13.sp,
                color = EcoTextSecondary
            )
        }

        IconButton(
            onClick = { /* Notifications screen will be added later */ },
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(EcoWhite)
        ) {
            Icon(
                imageVector = Icons.Default.NotificationsNone,
                contentDescription = "Notifications",
                tint = EcoNavy
            )
        }
    }
}

@Composable
private fun ImpactCard(
    co2Saved: Double,
    monthlyGoal: Int
) {
    val progress = (monthlyGoal / 100f).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(EcoGradientStart, EcoGradientEnd)
                )
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Recycling,
                contentDescription = null,
                tint = EcoWhite,
                modifier = Modifier.size(22.dp)
            )

            Text(
                text = "YOUR ENVIRONMENTAL IMPACT",
                color = EcoWhite.copy(alpha = 0.9f),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp
            )
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = String.format("%.1f", co2Saved),
                color = EcoWhite,
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "kg CO₂ saved",
                color = EcoWhite.copy(alpha = 0.9f),
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 7.dp)
            )
        }

        Text(
            text = "You're making a positive difference. Keep up the great work!",
            color = EcoWhite.copy(alpha = 0.9f),
            fontSize = 12.sp,
            lineHeight = 18.sp
        )

        EcoTrackProgress(
            progress = progress,
            trackColor = EcoWhite.copy(alpha = 0.25f),
            progressColor = EcoWhite
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Monthly goal progress",
                color = EcoWhite,
                fontSize = 11.sp
            )

            Text(
                text = "$monthlyGoal%",
                color = EcoWhite,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color
) {
    EcoTrackCard(modifier = modifier) {
        Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(EcoSurfaceBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(21.dp)
                )
            }

            Text(
                text = title,
                fontSize = 12.sp,
                color = EcoTextSecondary
            )

            Text(
                text = value,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = EcoNavy
            )

            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = EcoBlue
            )
        }
    }
}

@Composable
private fun WeeklyActivityCard(
    weeklySavings: List<Double>
) {
    val days = listOf("M", "T", "W", "T", "F", "S", "S")
    val maxValue = weeklySavings.maxOrNull()?.coerceAtLeast(0.01) ?: 1.0

    EcoTrackCard {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Weekly activity",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = EcoNavy
                )

                Text(
                    text = "Your CO₂ savings this week",
                    fontSize = 12.sp,
                    color = EcoTextSecondary
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(112.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                days.forEachIndexed { index, day ->
                    val saving = weeklySavings.getOrElse(index) { 0.0 }
                    val normalizedValue =
                        (saving / maxValue).toFloat().coerceIn(0f, 1f)

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Box(
                            modifier = Modifier
                                .height(85.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.48f)
                                    .height((85f * normalizedValue).coerceAtLeast(4f).dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (index == 5) EcoCoral
                                        else EcoBlue.copy(alpha = 0.75f)
                                    )
                            )
                        }

                        Spacer(modifier = Modifier.height(7.dp))

                        Text(
                            text = day,
                            fontSize = 11.sp,
                            color = EcoTextSecondary
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun DailyChallengeCard(
    onOpenMissions: () -> Unit
) {
    EcoTrackCard {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DAILY ECO CHALLENGE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = EcoBlue,
                    letterSpacing = 0.8.sp
                )

                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = EcoCoral,
                    modifier = Modifier.size(22.dp)
                )
            }

            Text(
                text = "Choose a greener commute",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = EcoNavy
            )

            Text(
                text = "Try walking, cycling or public transport for one journey today.",
                fontSize = 13.sp,
                color = EcoTextSecondary,
                lineHeight = 19.sp
            )

            EcoTrackProgress(
                progress = 0.4f,
                trackColor = EcoSurfaceBlue,
                progressColor = EcoBlue
            )

            Text(
                text = "Challenge progress: 1 of 2 journeys",
                fontSize = 11.sp,
                color = EcoTextSecondary
            )

            androidx.compose.material3.Button(
                onClick = onOpenMissions,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Explore All Missions")
            }
        }
    }
}

