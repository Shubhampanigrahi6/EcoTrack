package com.superhumans.ecotrack.data.repository

import android.content.Context
import com.superhumans.ecotrack.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class EcoDataRepository(private val context: Context) {

    suspend fun getEcoData(): Result<EcoData> =
        withContext(Dispatchers.IO) {
            try {
                val jsonText = context.assets
                    .open("eco_data.json")
                    .bufferedReader()
                    .use { it.readText() }

                val root = JSONObject(jsonText)

                val userJson = root.getJSONObject("user")
                val impactJson = root.getJSONObject("impact")
                val reportsJson = root.getJSONObject("reports")

                val user = EcoUser(
                    name = userJson.getString("name"),
                    email = userJson.getString("email"),
                    ecoPoints = userJson.getInt("ecoPoints"),
                    streak = userJson.getInt("streak")
                )

                val savingsJson = impactJson.getJSONArray("weeklySavings")
                val savings = List(savingsJson.length()) { index ->
                    savingsJson.getDouble(index)
                }

                val impact = EcoImpact(
                    co2Saved = impactJson.getDouble("co2Saved"),
                    monthlyGoal = impactJson.getInt("monthlyGoal"),
                    weeklySavings = savings
                )

                val leaderboardJson = root.getJSONArray("leaderboard")
                val leaderboard = List(leaderboardJson.length()) { index ->
                    val item = leaderboardJson.getJSONObject(index)
                    LeaderboardEntry(
                        name = item.getString("name"),
                        points = item.getInt("points")
                    )
                }

                val reports = EcoReports(
                    weeklyCo2Saved = reportsJson.getDouble("weeklyCo2Saved"),
                    monthlyCo2Saved = reportsJson.getDouble("monthlyCo2Saved"),
                    walkingKm = reportsJson.getDouble("walkingKm"),
                    cyclingKm = reportsJson.getDouble("cyclingKm"),
                    publicTransportKm = reportsJson.getDouble("publicTransportKm")
                )

                Result.success(
                    EcoData(
                        user = user,
                        impact = impact,
                        leaderboard = leaderboard,
                        reports = reports
                    )
                )
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}