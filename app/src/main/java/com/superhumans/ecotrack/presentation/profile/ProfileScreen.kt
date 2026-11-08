package com.superhumans.ecotrack.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superhumans.ecotrack.ui.theme.*

@Composable
fun ProfileScreen(
    onOpenReports: () -> Unit = {},
    onOpenSettings: () -> Unit = {}

) {

    var name by remember { mutableStateOf("Shubham Panigrahi") }
    var email by remember { mutableStateOf("shubham@example.com") }
    var showEditDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoBackground)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            text = "My Profile",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = EcoNavy
        )

        Spacer(modifier = Modifier.height(22.dp))

        // Profile card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = EcoWhite),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(92.dp)
                        .clip(CircleShape)
                        .background(EcoSurfaceBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = name.split(" ")
                            .mapNotNull { it.firstOrNull()?.toString() }
                            .take(2)
                            .joinToString(""),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = EcoBlue
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = name,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = EcoNavy
                )

                Text(
                    text = email,
                    fontSize = 13.sp,
                    color = EcoTextSecondary
                )

                Spacer(modifier = Modifier.height(18.dp))

                OutlinedButton(
                    onClick = { showEditDialog = true },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Edit Profile")
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Your Impact",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = EcoNavy
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileStatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Eco,
                value = "24.8 kg",
                label = "CO₂ Saved",
                iconColor = EcoGreen
            )

            ProfileStatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Stars,
                value = "1,240",
                label = "Eco Points",
                iconColor = EcoOrange
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileStatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.LocalFireDepartment,
                value = "7 Days",
                label = "Day Streak",
                iconColor = EcoCoral
            )

            ProfileStatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.EmojiEvents,
                value = "#12",
                label = "Global Rank",
                iconColor = EcoBlue
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Account",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = EcoNavy
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProfileMenuItem(
            icon = Icons.Default.BarChart,
            title = "My Reports",
            subtitle = "View your sustainability progress",
            onClick = onOpenReports
        )

        ProfileMenuItem(
            icon = Icons.Default.Settings,
            title = "Settings",
            subtitle = "Manage your app preferences",
            onClick = onOpenSettings
        )

        ProfileMenuItem(
            icon = Icons.Default.Lock,
            title = "Privacy & Security",
            subtitle = "Manage your account security",
            onClick = {}
        )

        ProfileMenuItem(
            icon = Icons.Default.HelpOutline,
            title = "Help & Support",
            subtitle = "Get assistance with EcoTrack",
            onClick = {}
        )

        ProfileMenuItem(
            icon = Icons.Default.Info,
            title = "About EcoTrack",
            subtitle = "Learn more about our mission",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Together for a greener planet 🌱",
            modifier = Modifier.fillMaxWidth(),
            color = EcoTextSecondary,
            fontSize = 13.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))
    }

    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Edit Profile") },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Full Name") },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showEditDialog = false }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showEditDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun ProfileStatCard(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String,
    iconColor: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = EcoWhite),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(25.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = EcoNavy
            )

            Text(
                text = label,
                fontSize = 12.sp,
                color = EcoTextSecondary
            )
        }
    }
}

@Composable
private fun ProfileMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(containerColor = EcoWhite),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .background(EcoSurfaceBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = EcoBlue
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    color = EcoNavy,
                    fontSize = 14.sp
                )
                Text(
                    text = subtitle,
                    color = EcoTextSecondary,
                    fontSize = 11.sp
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = EcoTextSecondary
            )
        }
    }
}
