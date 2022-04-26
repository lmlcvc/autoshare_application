package com.riteh.autoshare.repository

import com.riteh.autoshare.data.UserPreferences
import com.riteh.autoshare.network.AuthApi
import com.riteh.autoshare.responses.User

class AuthRepository(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository() {
    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }

    suspend fun saveUserDetails(user: User) {
        preferences.saveUserDetails(user)
    }

    suspend fun userSignUp(
        name: String,
        surname: String,
        email: String,
        password: String
    ) = safeApiCall {
        api.userSignUp(name, surname, email, password)
    }
}