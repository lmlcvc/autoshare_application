package com.riteh.autoshare.repository

import com.riteh.autoshare.data.UserPreferences
import com.riteh.autoshare.network.AuthApi

class AuthRepository(
    private val api: AuthApi,
    private val preferences: UserPreferences
): BaseRepository() {
    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email,password)
    }

    /**
     * Save token from server to local shared preferences
     */
    suspend fun saveAuthToken(token: String){
        preferences.saveAuthToken(token)
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