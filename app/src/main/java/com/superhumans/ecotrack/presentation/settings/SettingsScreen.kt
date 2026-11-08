
package com.superhumans.ecotrack.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superhumans.ecotrack.ui.theme.*

@Composable
fun SettingsScreen(
    onLogout: () -> Unit = {}
) {
    var notifications by remember { mutableStateOf(true) }
    var reminders by remember { mutableStateOf(true) }
    var darkMode by remember { mutableStateOf(false) }
    var locationSharing by remember { mutableStateOf(true) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(EcoBackground)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Settings",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = EcoNavy
        )

        Spacer(Modifier.height(22.dp))

        SettingsSectionTitle("Preferences")

        SettingsToggle(
            icon = Icons.Default.Notifications,
            title = "Push Notifications",
            subtitle = "Receive community and activity updates",
            checked = notifications,
            onCheckedChange = { notifications = it }
        )

        SettingsToggle(
            icon = Icons.Default.Alarm,
            title = "Daily Reminders",
            subtitle = "Get reminders for your eco goals",
            checked = reminders,
            onCheckedChange = { reminders = it }
        )

        SettingsToggle(
            icon = Icons.Default.DarkMode,
            title = "Dark Mode",
            subtitle = "Use a darker appearance",
            checked = darkMode,
            onCheckedChange = { darkMode = it }
        )

        Spacer(Modifier.height(20.dp))

        SettingsSectionTitle("Privacy")

        SettingsToggle(
            icon = Icons.Default.LocationOn,
            title = "Location Sharing",
            subtitle = "Allow friends to see your location",
            checked = locationSharing,
            onCheckedChange = { locationSharing = it }
        )

        Spacer(Modifier.height(20.dp))

        SettingsSectionTitle("Support")

        SettingsAction(
            icon = Icons.Default.PrivacyTip,
            title = "Privacy Policy",
            subtitle = "Learn how your data is handled",
            onClick = {}
        )

        SettingsAction(
            icon = Icons.Default.HelpOutline,
            title = "Help & Support",
            subtitle = "Get help with EcoTrack",
            onClick = {}
        )

        SettingsAction(
            icon = Icons.Default.Info,
            title = "About EcoTrack",
            subtitle = "Version 1.0.0",
            onClick = {}
        )

        Spacer(Modifier.height(24.dp))

        OutlinedButton(
            onClick = { showLogoutDialog = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = EcoError
            )
        ) {
            Icon(Icons.Default.Logout, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Log Out")
        }

        Spacer(Modifier.height(20.dp))
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Log out?") },
            text = { Text("Are you sure you want to log out of EcoTrack?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    }
                ) {
                    Text("Log Out", color = EcoError)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        color = EcoNavy,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
private fun SettingsToggle(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(containerColor = EcoWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = EcoBlue
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    title,
                    color = EcoNavy,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    subtitle,
                    color = EcoTextSecondary,
                    fontSize = 11.sp
                )
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
private fun SettingsAction(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(containerColor = EcoWhite)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = EcoBlue)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 14.dp)
            ) {
                Text(
                    title,
                    color = EcoNavy,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    subtitle,
                    color = EcoTextSecondary,
                    fontSize = 11.sp
                )
            }

            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = EcoTextSecondary
            )
        }
    }
}
