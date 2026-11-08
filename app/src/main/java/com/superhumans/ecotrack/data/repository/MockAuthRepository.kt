
package com.superhumans.ecotrack.data.repository

import com.superhumans.ecotrack.data.model.AuthUser
import kotlinx.coroutines.delay

class MockAuthRepository : AuthRepository {

    private val users = mutableListOf(
        AuthUser(
            id = "1",
            name = "Shubham",
            email = "demo@ecotrack.com"
        )
    )

    private val passwords = mutableMapOf(
        "demo@ecotrack.com" to "123456"
    )

    override suspend fun login(
        email: String,
        password: String
    ): Result<AuthUser> {
        delay(500)

        val normalizedEmail = email.trim().lowercase()
        val user = users.find {
            it.email.equals(normalizedEmail, ignoreCase = true)
        }

        return if (user != null && passwords[normalizedEmail] == password) {
            Result.success(user)
        } else {
            Result.failure(
                Exception("Invalid email or password")
            )
        }
    }

    override suspend fun signup(
        name: String,
        email: String,
        password: String
    ): Result<AuthUser> {
        delay(500)

        val normalizedEmail = email.trim().lowercase()

        if (users.any {
                it.email.equals(normalizedEmail, ignoreCase = true)
            }) {
            return Result.failure(
                Exception("An account with this email already exists")
            )
        }

        val user = AuthUser(
            id = System.currentTimeMillis().toString(),
            name = name.trim(),
            email = normalizedEmail
        )

        users.add(user)
        passwords[normalizedEmail] = password

        return Result.success(user)
    }

    override suspend fun logout() {
        // Mock logout. Session state will be cleared by the ViewModel.
    }
}
