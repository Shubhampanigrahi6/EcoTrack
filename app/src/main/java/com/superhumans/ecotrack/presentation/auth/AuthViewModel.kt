
package com.superhumans.ecotrack.presentation.auth

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.superhumans.ecotrack.data.model.AuthUser
import com.superhumans.ecotrack.data.repository.AuthRepository
import com.superhumans.ecotrack.data.repository.MockAuthRepository
import com.superhumans.ecotrack.data.repository.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val user: AuthUser? = null,
    val error: String? = null,
    val isAuthenticated: Boolean = false
)

class AuthViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository: AuthRepository = MockAuthRepository()

    private val sessionManager = SessionManager(application)

    private val savedUser = sessionManager.getLoggedInUser()

    private val _uiState = MutableStateFlow(
        AuthUiState(
            user = savedUser,
            isAuthenticated = savedUser != null
        )
    )

    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = _uiState.value.copy(
                error = "Please enter email and password"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            repository.login(email, password)
                .onSuccess { user ->
                    sessionManager.saveSession(user)

                    _uiState.value = AuthUiState(
                        user = user,
                        isAuthenticated = true
                    )
                }
                .onFailure { exception ->
                    _uiState.value = AuthUiState(
                        error = exception.message ?: "Login failed"
                    )
                }
        }
    }

    fun signup(name: String, email: String, password: String) {
        when {
            name.isBlank() -> {
                _uiState.value = _uiState.value.copy(
                    error = "Please enter your name"
                )
                return
            }

            !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> {
                _uiState.value = _uiState.value.copy(
                    error = "Please enter a valid email address"
                )
                return
            }

            password.length < 6 -> {
                _uiState.value = _uiState.value.copy(
                    error = "Password must be at least 6 characters"
                )
                return
            }
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            repository.signup(name, email, password)
                .onSuccess { user ->
                    sessionManager.saveSession(user)

                    _uiState.value = AuthUiState(
                        user = user,
                        isAuthenticated = true
                    )
                }
                .onFailure { exception ->
                    _uiState.value = AuthUiState(
                        error = exception.message ?: "Signup failed"
                    )
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun logout() {
        // Clear the saved session immediately.
        sessionManager.clearSession()

        _uiState.value = AuthUiState()

        viewModelScope.launch {
            repository.logout()
        }
    }
}
