package com.superhumans.ecotrack.data.model

data class EcoUser(
    val name: String,
    val email: String,
    val ecoPoints: Int,
    val streak: Int
)

data class EcoImpact(
    val co2Saved: Double,
    val monthlyGoal: Int,
    val weeklySavings: List<Double>
)

data class LeaderboardEntry(
    val name: String,
    val points: Int
)

data class EcoReports(
    val weeklyCo2Saved: Double,
    val monthlyCo2Saved: Double,
    val walkingKm: Double,
    val cyclingKm: Double,
    val publicTransportKm: Double
)

data class EcoData(
    val user: EcoUser,
    val impact: EcoImpact,
    val leaderboard: List<LeaderboardEntry>,
    val reports: EcoReports
)