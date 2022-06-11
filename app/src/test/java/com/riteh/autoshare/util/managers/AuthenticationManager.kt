package com.riteh.autoshare.util.managers

import com.riteh.autoshare.network.AuthApi
import com.riteh.autoshare.responses.LoginResponse
import com.riteh.autoshare.responses.SignUpResponse
import com.riteh.autoshare.responses.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AuthenticationManager(endpoint: String) {
    private var user: User? = null
    private var token: String? = null

    var signUpName: String? = null
    var signUpSurname: String? = null
    var signUpEmail: String? = null
    var signUpPassword: String? = null

    private val okHttpClient = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(endpoint)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private val api = retrofit.create(AuthApi::class.java)

    suspend fun loginBlocking(): LoginResponse {
        val loginResponse = api.login(email, password)

        user = loginResponse.user
        token = loginResponse.token
        return loginResponse
    }

    suspend fun signUpBlocking(): SignUpResponse {
        val signUpResponse = api.userSignUp(name, surname, email, password)

        signUpName = signUpResponse.name
        signUpSurname = signUpResponse.surname
        signUpEmail = signUpResponse.email
        signUpPassword = signUpResponse.password
        return signUpResponse
    }

    fun getUserFromManager(): User? {
        return this.user
    }

    fun getTokenFromManager(): String? {
        return this.token
    }

    companion object {
        const val name = "name"
        const val surname = "surname"
        const val email = "fake_address@gmail.com"
        const val password = "hrfuqai@8979457"
    }
}

