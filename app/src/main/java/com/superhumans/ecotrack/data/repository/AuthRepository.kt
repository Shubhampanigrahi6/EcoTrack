package com.superhumans.ecotrack.data.repository

import com.superhumans.ecotrack.data.model.AuthUser

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String
    ): Result<AuthUser>

    suspend fun signup(
        name: String,
        email: String,
        password: String
    ): Result<AuthUser>

    suspend fun logout()
}
