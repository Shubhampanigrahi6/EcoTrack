package com.superhumans.ecotrack.data.repository

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MockAuthRepositoryTest {

    private val repository = MockAuthRepository()

    @Test
    fun loginWithValidCredentialsReturnsUser() = runBlocking {
        val result = repository.login(
            email = "demo@ecotrack.com",
            password = "123456"
        )

        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull())
        assertEquals("Shubham", result.getOrNull()?.name)
    }

    @Test
    fun loginWithInvalidCredentialsReturnsFailure() = runBlocking {
        val result = repository.login(
            email = "wrong@email.com",
            password = "wrong"
        )

        assertTrue(result.isFailure)
    }

    @Test
    fun signupWithNewEmailCreatesUser() = runBlocking {
        val result = repository.signup(
            name = "Test User",
            email = "test@example.com",
            password = "123456"
        )

        assertTrue(result.isSuccess)
        assertEquals("Test User", result.getOrNull()?.name)
    }

    @Test
    fun signupWithExistingEmailReturnsFailure() = runBlocking {
        val result = repository.signup(
            name = "Another User",
            email = "demo@ecotrack.com",
            password = "123456"
        )

        assertTrue(result.isFailure)
    }
}