package com.riteh.autoshare.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.riteh.autoshare.responses.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(context: Context) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(
            name = "my_data_store"
        )
    }

    val authToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    suspend fun saveAuthToken(authToken: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    suspend fun saveUserDetails(user: User) {
        dataStore.edit { preferences ->
            preferences[ID] = user.id.toString()
            preferences[EMAIL] = user.email
            preferences[NAME] = user.name
            preferences[SURNAME] = user.surname
            preferences[DATE_OF_BIRTH] = user.date_of_birth.toString()
            preferences[LICENSE_ID] = user.license_id
            preferences[RENTER_AVG_RATING] = user.renter_avg_rating.toString()
            preferences[RENTEE_AVG_RATING] = user.rentee_avg_rating.toString()
        }
    }

    companion object {
        private val KEY_AUTH = preferencesKey<String>("key_auth")

        private val ID = preferencesKey<String>("id")
        private val EMAIL = preferencesKey<String>("email")
        private val NAME = preferencesKey<String>("name")
        private val SURNAME = preferencesKey<String>("surname")
        private val DATE_OF_BIRTH = preferencesKey<String>("date_of_birth")
        private val LICENSE_ID = preferencesKey<String>("license_id")
        private val RENTER_AVG_RATING = preferencesKey<String>("renter_avg_rating")
        private val RENTEE_AVG_RATING = preferencesKey<String>("rentee_avg_rating")
    }
}