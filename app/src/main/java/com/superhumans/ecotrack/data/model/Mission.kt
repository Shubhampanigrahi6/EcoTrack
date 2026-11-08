package com.superhumans.ecotrack.data.model

data class Mission(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val co2Saved: Double,
    val ecoPoints: Int,
    val isCompleted: Boolean = false
)