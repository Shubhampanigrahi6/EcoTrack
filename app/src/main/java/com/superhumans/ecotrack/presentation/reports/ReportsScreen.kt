
package com.superhumans.ecotrack.presentation.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Train
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superhumans.ecotrack.presentation.home.EcoDataViewModel
import com.superhumans.ecotrack.ui.theme.*

@Composable
fun ReportsScreen(
    ecoViewModel: EcoDataViewModel = viewModel()
) {
    val uiState by ecoViewModel.uiState.collectAsState()
    var selectedPeriod by remember { mutableStateOf("Weekly") }

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
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = uiState.error ?: "Unable to load reports",
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
            val reports = uiState.data!!.reports

            val co2Saved = if (selectedPeriod == "Weekly") {
                reports.weeklyCo2Saved
            } else {
                reports.monthlyCo2Saved
            }

            val walking = reports.walkingKm
            val cycling = reports.cyclingKm
            val publicTransport = reports.publicTransportKm

            val totalDistance = walking + cycling + publicTransport

            val walkingPercentage =
                if (totalDistance > 0) (walking / totalDistance * 100).toInt() else 0

            val cyclingPercentage =
                if (totalDistance > 0) (cycling / totalDistance * 100).toInt() else 0

            val transportPercentage =
                if (totalDistance > 0) {
                    100 - walkingPercentage - cyclingPercentage
                } else 0

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(EcoBackground)
                    .padding(18.dp)
            ) {
                Text(
                    text = "Your Impact 📊",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = EcoNavy
                )

                Text(
                    text = "Track your sustainability progress",
                    color = EcoTextSecondary,
                    fontSize = 13.sp
                )

                Spacer(Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    listOf("Weekly", "Monthly").forEach { period ->
                        FilterChip(
                            selected = selectedPeriod == period,
                            onClick = { selectedPeriod = period },
                            label = { Text(period) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = EcoBlue,
                                selectedLabelColor = EcoWhite
                            )
                        )
                    }
                }

                Spacer(Modifier.height(18.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = EcoBlue
                    )
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Eco,
                                contentDescription = null,
                                tint = EcoWhite
                            )

                            Spacer(Modifier.width(8.dp))

                            Text(
                                "Total CO₂ saved",
                                color = EcoWhite,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = String.format("%.1f kg", co2Saved),
                            color = EcoWhite,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            "Compared with conventional travel",
                            color = EcoWhite.copy(alpha = 0.8f),
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricCard(
                        title = "Distance",
                        value = String.format("%.1f km", totalDistance),
                        modifier = Modifier.weight(1f)
                    )

                    MetricCard(
                        title = "Eco Trips",
                        value = if (selectedPeriod == "Weekly") "12" else "48",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(22.dp))

                Text(
                    "Transport Breakdown",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = EcoNavy
                )

                Spacer(Modifier.height(12.dp))

                TransportRow(
                    icon = {
                        Icon(
                            Icons.Default.DirectionsWalk,
                            contentDescription = null,
                            tint = EcoBlue
                        )
                    },
                    title = "Walking",
                    subtitle = String.format("%.1f km", walking),
                    value = walkingPercentage,
                    valueLabel = "$walkingPercentage%"
                )

                TransportRow(
                    icon = {
                        Icon(
                            Icons.Default.DirectionsBike,
                            contentDescription = null,
                            tint = EcoGreen
                        )
                    },
                    title = "Cycling",
                    subtitle = String.format("%.1f km", cycling),
                    value = cyclingPercentage,
                    valueLabel = "$cyclingPercentage%"
                )

                TransportRow(
                    icon = {
                        Icon(
                            Icons.Default.Train,
                            contentDescription = null,
                            tint = EcoOrange
                        )
                    },
                    title = "Public transport",
                    subtitle = String.format("%.1f km", publicTransport),
                    value = transportPercentage,
                    valueLabel = "$transportPercentage%"
                )
            }
        }

        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(EcoBackground),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No report data available",
                    color = EcoTextSecondary
                )
            }
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = EcoWhite
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                title,
                color = EcoTextSecondary,
                fontSize = 12.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                value,
                color = EcoNavy,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun TransportRow(
    icon: @Composable () -> Unit,
    title: String,
    subtitle: String,
    value: Int,
    valueLabel: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = EcoWhite
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    title,
                    color = EcoNavy,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    subtitle,
                    color = EcoTextSecondary,
                    fontSize = 11.sp
                )
            }

            Text(
                valueLabel,
                color = EcoBlue,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
