
package com.superhumans.ecotrack.data.repository

import android.content.Context
import com.superhumans.ecotrack.data.model.AuthUser

class SessionManager(context: Context) {

    private val preferences = context.getSharedPreferences(
        "ecotrack_session",
        Context.MODE_PRIVATE
    )

    fun saveSession(user: AuthUser) {
        preferences.edit()
            .putString("user_id", user.id)
            .putString("user_name", user.name)
            .putString("user_email", user.email)
            .putBoolean("is_logged_in", true)
            .apply()
    }

    fun getLoggedInUser(): AuthUser? {
        if (!isLoggedIn()) return null

        val id = preferences.getString("user_id", null) ?: return null
        val name = preferences.getString("user_name", null) ?: return null
        val email = preferences.getString("user_email", null) ?: return null

        return AuthUser(id, name, email)
    }

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean("is_logged_in", false)
    }

    fun clearSession() {
        preferences.edit().clear().apply()
    }
}
