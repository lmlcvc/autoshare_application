package com.riteh.autoshare.repository

import com.riteh.autoshare.network.UserApi
import java.util.*

class UserRepository(
    private val api: UserApi,
) : BaseRepository() {

    suspend fun userUpdate(
        id: Int,
        name: String,
        surname: String,
        email: String,
        license_id: String,
        date_of_birth: Date
    ) = safeApiCall { api.userUpdate(id, name, surname, email, license_id, date_of_birth) }
}