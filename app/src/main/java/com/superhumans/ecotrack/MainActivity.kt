package com.superhumans.ecotrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.superhumans.ecotrack.navigation.EcoTrackApp
import com.superhumans.ecotrack.ui.theme.EcoTrackTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EcoTrackTheme {
                EcoTrackApp()
            }
        }
    }
}