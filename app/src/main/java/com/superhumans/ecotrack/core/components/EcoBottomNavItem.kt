
package com.superhumans.ecotrack.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import com.superhumans.ecotrack.ui.theme.EcoBlue
import com.superhumans.ecotrack.ui.theme.EcoTextLight
import com.superhumans.ecotrack.ui.theme.EcoWhite

data class EcoBottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun EcoTrackBottomBar(
    items: List<EcoBottomNavItem>,
    selectedRoute: String,
    onItemSelected: (String) -> Unit
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = EcoWhite
    ) {
        items.forEach { item ->
            val selected = selectedRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = { onItemSelected(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(item.label, fontSize = 10.sp)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = EcoBlue,
                    selectedTextColor = EcoBlue,
                    unselectedIconColor = EcoTextLight,
                    unselectedTextColor = EcoTextLight,
                    indicatorColor = EcoBlue.copy(alpha = 0.12f)
                )
            )
        }
    }
}
