package com.superhumans.ecotrack.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.superhumans.ecotrack.data.model.EcoData
import com.superhumans.ecotrack.data.model.Mission
import com.superhumans.ecotrack.data.repository.EcoDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class EcoDataUiState(
    val isLoading: Boolean = false,
    val data: EcoData? = null,
    val error: String? = null
)

class EcoDataViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = EcoDataRepository(application)

    private val _uiState = MutableStateFlow(EcoDataUiState())
    val uiState: StateFlow<EcoDataUiState> = _uiState.asStateFlow()

    init {
        loadEcoData()
    }

    fun loadEcoData() {
        viewModelScope.launch {
            _uiState.value = EcoDataUiState(isLoading = true)

            repository.getEcoData()
                .onSuccess { result ->
                    _uiState.value = EcoDataUiState(data = result)
                }
                .onFailure { exception ->
                    _uiState.value = EcoDataUiState(
                        error = exception.message ?: "Unable to load data"
                    )
                }
        }
    }



    fun addMissionReward(mission: Mission) {
        val currentState = _uiState.value
        val currentData = currentState.data ?: return

        val todayIndex = (
                java.util.Calendar.getInstance().get(
                    java.util.Calendar.DAY_OF_WEEK
                ) + 5
                ) % 7

        val updatedWeeklySavings =
            currentData.impact.weeklySavings.toMutableList()

        if (todayIndex in updatedWeeklySavings.indices) {
            updatedWeeklySavings[todayIndex] += mission.co2Saved
        }

        val updatedUser = currentData.user.copy(
            ecoPoints = currentData.user.ecoPoints + mission.ecoPoints
        )

        val updatedImpact = currentData.impact.copy(
            co2Saved = currentData.impact.co2Saved + mission.co2Saved,
            weeklySavings = updatedWeeklySavings
        )

        val updatedReports = currentData.reports.copy(
            weeklyCo2Saved =
                currentData.reports.weeklyCo2Saved + mission.co2Saved,
            monthlyCo2Saved =
                currentData.reports.monthlyCo2Saved + mission.co2Saved
        )

        val updatedLeaderboard = currentData.leaderboard.map { entry ->
            if (entry.name.equals(updatedUser.name, ignoreCase = true)) {
                entry.copy(points = entry.points + mission.ecoPoints)
            } else {
                entry
            }
        }

        val updatedData = currentData.copy(
            user = updatedUser,
            impact = updatedImpact,
            reports = updatedReports,
            leaderboard = updatedLeaderboard
        )

        _uiState.value = currentState.copy(data = updatedData)
    }

}