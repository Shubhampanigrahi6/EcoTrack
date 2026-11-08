
package com.superhumans.ecotrack.presentation.community.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.superhumans.ecotrack.ui.theme.EcoBackground
import com.superhumans.ecotrack.ui.theme.EcoBlue
import com.superhumans.ecotrack.ui.theme.EcoNavy
import com.superhumans.ecotrack.ui.theme.EcoTextSecondary
import com.superhumans.ecotrack.ui.theme.EcoWhite

private data class DemoFriend(
    val id: Int,
    val name: String,
    val activity: String,
    val position: LatLng
)

private val demoFriends = listOf(
    DemoFriend(
        1,
        "Aarav Sharma",
        "Cycling",
        LatLng(28.4490, 77.1002)
    ),
    DemoFriend(
        2,
        "Priya Das",
        "Walking",
        LatLng(28.4455, 77.0968)
    ),
    DemoFriend(
        3,
        "Rahul Verma",
        "Public transport",
        LatLng(28.4481, 77.0955)
    ),
    DemoFriend(
        4,
        "sriya patro",
        "Cycling",
        LatLng(28.4448, 77.1010)
    )
)

@Composable
fun MapScreen() {

    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var currentLocation by remember {
        mutableStateOf<LatLng?>(null)
    }

    var selectedFriend by remember {
        mutableStateOf<DemoFriend?>(null)
    }

    val demoCenter = LatLng(
        28.446992683450745,
        77.09887384031231
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(demoCenter, 14f)
    }

    fun fetchCurrentLocation() {
        if (!hasLocationPermission) return

        try {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                CancellationTokenSource().token
            ).addOnSuccessListener { location ->
                location?.let {
                    val position = LatLng(it.latitude, it.longitude)
                    currentLocation = position

                    cameraPositionState.position =
                        CameraPosition.fromLatLngZoom(position, 15f)
                }
            }
        } catch (_: SecurityException) {
            hasLocationPermission = false
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (hasLocationPermission) {
            fetchCurrentLocation()
        }
    }

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            fetchCurrentLocation()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = hasLocationPermission
            ),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = false,
                compassEnabled = true,
                mapToolbarEnabled = false
            )
        ) {
            demoFriends.forEach { friend ->
                Marker(
                    state = MarkerState(position = friend.position),
                    title = friend.name,
                    snippet = friend.activity,
                    onClick = {
                        selectedFriend = friend
                        true
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = EcoWhite,
                shadowElevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Nearby Friends",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = EcoNavy
                    )

                    Text(
                        text = "${demoFriends.size} friends active nearby",
                        fontSize = 12.sp,
                        color = EcoTextSecondary
                    )
                }
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            shape = CircleShape,
            color = EcoWhite,
            shadowElevation = 5.dp
        ) {
            androidx.compose.material3.IconButton(
                onClick = {
                    if (hasLocationPermission) {
                        fetchCurrentLocation()
                    } else {
                        permissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = "Find my location",
                    tint = EcoBlue
                )
            }
        }

        selectedFriend?.let { friend ->
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(22.dp),
                color = EcoWhite,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(EcoBackground),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = EcoBlue
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = friend.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = EcoNavy
                        )

                        Text(
                            text = "Activity: ${friend.activity}",
                            fontSize = 12.sp,
                            color = EcoTextSecondary
                        )
                    }

                    Button(
                        onClick = { selectedFriend = null },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EcoBlue
                        )
                    ) {
                        Text("Close", color = EcoWhite)
                    }
                }
            }
        }
    }
}
