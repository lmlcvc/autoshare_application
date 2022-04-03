package com.riteh.autoshare.repository

import com.riteh.autoshare.network.AuthApi

class AuthRepository(
    private val api: AuthApi
): BaseRepository() {
    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email,password)
    }
}