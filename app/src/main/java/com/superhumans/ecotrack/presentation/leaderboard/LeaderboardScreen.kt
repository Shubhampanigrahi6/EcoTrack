
package com.superhumans.ecotrack.presentation.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superhumans.ecotrack.presentation.home.EcoDataViewModel
import com.superhumans.ecotrack.ui.theme.*

private data class LeaderboardUser(
    val name: String,
    val points: Int,
    val activity: String,
    val initials: String
)

@Composable
fun LeaderboardScreen(
    ecoViewModel: EcoDataViewModel = viewModel()
) {
    val uiState by ecoViewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf("Weekly") }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(EcoBackground),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = EcoBlue)
            }
        }

        uiState.error != null -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(EcoBackground),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = uiState.error ?: "Unable to load leaderboard",
                        color = EcoNavy
                    )

                    TextButton(
                        onClick = { ecoViewModel.loadEcoData() }
                    ) {
                        Text("Retry")
                    }
                }
            }
        }

        uiState.data != null -> {
            val ecoData = uiState.data!!
            val currentUserName = ecoData.user.name

            val baseUsers = ecoData.leaderboard.map { entry ->
                LeaderboardUser(
                    name = entry.name,
                    points = entry.points,
                    activity = if (entry.name.equals(
                            currentUserName,
                            ignoreCase = true
                        )
                    ) "Your eco journey" else "Eco community member",
                    initials = entry.name
                        .split(" ")
                        .mapNotNull { it.firstOrNull()?.toString() }
                        .take(2)
                        .joinToString("")
                        .uppercase()
                )
            }

            val users = when (selectedTab) {
                "Monthly" -> baseUsers.map {
                    it.copy(points = (it.points * 3.2).toInt())
                }.sortedByDescending { it.points }

                else -> baseUsers.sortedByDescending { it.points }
            }

            val currentRank = users.indexOfFirst {
                it.name.equals(currentUserName, ignoreCase = true)
            } + 1

            val currentUserPoints = users.firstOrNull {
                it.name.equals(currentUserName, ignoreCase = true)
            }?.points ?: 0

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(EcoBackground)
                    .padding(horizontal = 18.dp)
            ) {
                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Leaderboard 🏆",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = EcoNavy
                )

                Text(
                    text = "Celebrate your sustainable impact",
                    fontSize = 13.sp,
                    color = EcoTextSecondary
                )

                Spacer(Modifier.height(20.dp))

                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = EcoWhite,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = EcoOrange,
                                modifier = Modifier.size(28.dp)
                            )

                            Spacer(Modifier.width(10.dp))

                            Text(
                                text = "Your Ranking",
                                fontWeight = FontWeight.SemiBold,
                                color = EcoNavy
                            )
                        }

                        Spacer(Modifier.height(14.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = if (currentRank > 0) "#$currentRank" else "—",
                                    fontSize = 34.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EcoBlue
                                )

                                Text(
                                    text = "Current position",
                                    color = EcoTextSecondary,
                                    fontSize = 12.sp
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = currentUserPoints.toString(),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EcoNavy
                                )

                                Text(
                                    text = "Eco points",
                                    color = EcoTextSecondary,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    listOf("Weekly", "Monthly").forEach { tab ->
                        val selected = selectedTab == tab

                        FilterChip(
                            selected = selected,
                            onClick = { selectedTab = tab },
                            label = { Text(tab) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EcoBlue,
                                selectedLabelColor = EcoWhite
                            )
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Top Eco Warriors",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = EcoNavy
                )

                Spacer(Modifier.height(10.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    itemsIndexed(users) { index, user ->
                        val isCurrentUser = user.name.equals(
                            currentUserName,
                            ignoreCase = true
                        )

                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = if (isCurrentUser) {
                                EcoSurfaceBlue
                            } else {
                                EcoWhite
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = when (index) {
                                        0 -> "🥇"
                                        1 -> "🥈"
                                        2 -> "🥉"
                                        else -> "#${index + 1}"
                                    },
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.width(42.dp)
                                )

                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(EcoBlue.copy(alpha = 0.12f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = user.initials,
                                        color = EcoBlue,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(Modifier.width(12.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = user.name,
                                        fontWeight = FontWeight.SemiBold,
                                        color = EcoNavy
                                    )

                                    Text(
                                        text = user.activity,
                                        fontSize = 11.sp,
                                        color = EcoTextSecondary
                                    )
                                }

                                Column(
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Text(
                                        text = user.points.toString(),
                                        fontWeight = FontWeight.Bold,
                                        color = EcoBlue
                                    )

                                    Text(
                                        text = "points",
                                        fontSize = 10.sp,
                                        color = EcoTextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(EcoBackground),
                contentAlignment = Alignment.Center
            ) {
                Text("No leaderboard data available", color = EcoTextSecondary)
            }
        }
    }
}
