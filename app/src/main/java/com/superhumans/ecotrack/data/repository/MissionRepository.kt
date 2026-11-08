
package com.superhumans.ecotrack.data.repository

import android.content.Context
import com.superhumans.ecotrack.data.model.Mission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MissionRepository(private val context: Context) {

    suspend fun getMissions(): Result<List<Mission>> =
        withContext(Dispatchers.IO) {
            try {
                val jsonText = context.assets
                    .open("missions.json")
                    .bufferedReader()
                    .use { it.readText() }

                val root = JSONObject(jsonText)
                val missionsJson = root.getJSONArray("missions")

                val missions = List(missionsJson.length()) { index ->
                    val item = missionsJson.getJSONObject(index)

                    Mission(
                        id = item.getString("id"),
                        title = item.getString("title"),
                        description = item.getString("description"),
                        category = item.getString("category"),
                        co2Saved = item.getDouble("co2Saved"),
                        ecoPoints = item.getInt("ecoPoints")
                    )
                }

                Result.success(missions)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
