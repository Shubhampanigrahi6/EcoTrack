
package com.superhumans.ecotrack.presentation.missions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.superhumans.ecotrack.data.model.Mission
import com.superhumans.ecotrack.data.repository.MissionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MissionsUiState(
    val isLoading: Boolean = false,
    val missions: List<Mission> = emptyList(),
    val error: String? = null
)

class MissionsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = MissionRepository(application)

    private val _uiState = MutableStateFlow(MissionsUiState())
    val uiState: StateFlow<MissionsUiState> = _uiState.asStateFlow()

    init {
        loadMissions()
    }

    fun loadMissions() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            repository.getMissions()
                .onSuccess { missions ->
                    _uiState.value = MissionsUiState(
                        missions = missions
                    )
                }
                .onFailure { exception ->
                    _uiState.value = MissionsUiState(
                        error = exception.message ?: "Unable to load missions"
                    )
                }
        }
    }

    fun completeMission(missionId: String) {
        val currentState = _uiState.value

        val mission = currentState.missions.find {
            it.id == missionId
        } ?: return

        if (mission.isCompleted) return

        val updatedMissions = currentState.missions.map {
            if (it.id == missionId) {
                it.copy(isCompleted = true)
            } else {
                it
            }
        }

        _uiState.value = currentState.copy(
            missions = updatedMissions
        )
    }
}
