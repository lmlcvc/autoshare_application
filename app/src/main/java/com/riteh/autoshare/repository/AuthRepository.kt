package com.riteh.autoshare.repository

import com.riteh.autoshare.network.AuthApi

class AuthRepository(
    private val api: AuthApi,
) : BaseRepository() {

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
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