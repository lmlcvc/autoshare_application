package com.riteh.autoshare.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.riteh.autoshare.data.PreferencesKeys.EMAIL
import com.riteh.autoshare.data.PreferencesKeys.ID
import com.riteh.autoshare.data.PreferencesKeys.KEY_AUTH
import com.riteh.autoshare.data.PreferencesKeys.NAME
import com.riteh.autoshare.data.PreferencesKeys.RENTEE_AVG_RATING
import com.riteh.autoshare.data.PreferencesKeys.RENTER_AVG_RATING
import com.riteh.autoshare.data.PreferencesKeys.SURNAME
import com.riteh.autoshare.responses.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class UserPreferences(context: Context) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences> = applicationContext.createDataStore(
        name = "user_info"
    )

    suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = token
        }
    }

    suspend fun saveUserInfo(user: User) {
        dataStore.edit { preferences ->
            preferences[ID] = user.id.toString()
            preferences[EMAIL] = user.email
            preferences[NAME] = user.name
            preferences[SURNAME] = user.surname

            // TODO: add DOB & license fields
            // preferences[DATE_OF_BIRTH] = user.date_of_birth.toString()
            // preferences[LICENSE_ID] = user.license_id.toString()

            preferences[RENTER_AVG_RATING] = user.renter_avg_rating.toString()
            preferences[RENTEE_AVG_RATING] = user.rentee_avg_rating.toString()
        }
    }

    suspend fun updateUserInfo(name: String, surname: String, email: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL] = email
            preferences[NAME] = name
            preferences[SURNAME] = surname

            // TODO: add DOB & license fields
            // preferences[DATE_OF_BIRTH] = user.date_of_birth.toString()
            // preferences[LICENSE_ID] = user.license_id.toString()

        }
    }


    fun getTokenFromDataStore() = dataStore.data.map { preferences ->
        preferences[KEY_AUTH] ?: ""
    }


    fun getUserFromDataStore() : Flow<User> = dataStore.data.map { preferences ->
        User(
            id = preferences[ID]?.toInt() ?: -1,
            email = preferences[EMAIL] ?: "",
            name = preferences[NAME] ?: "",
            surname = preferences[SURNAME] ?: "",
            date_of_birth = Date(2000-1900, 0, 1), // TODO: fix when DOB & license fields added
            license_id = "24543643",
            renter_avg_rating = preferences[RENTER_AVG_RATING]?.toDouble() ?: 0.0,
            rentee_avg_rating = preferences[RENTEE_AVG_RATING]?.toDouble() ?: 0.0,
        )

        // preferences[EMAIL]?.let { it1 -> Log.i("getuserfromdatastore", it1) }
    }
}


private object PreferencesKeys {
    val ID = preferencesKey<String>("id")
    val EMAIL = preferencesKey<String>("email")
    val NAME = preferencesKey<String>("name")
    val SURNAME = preferencesKey<String>("surname")
    val DATE_OF_BIRTH = preferencesKey<String>("date_of_birth")
    val LICENSE_ID = preferencesKey<String>("license_id")
    val RENTER_AVG_RATING = preferencesKey<String>("renter_avg_rating")
    val RENTEE_AVG_RATING = preferencesKey<String>("rentee_avg_rating")

    val KEY_AUTH = preferencesKey<String>("key_auth")
}
