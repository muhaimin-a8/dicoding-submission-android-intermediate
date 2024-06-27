package me.muhaimin.storyapp.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>){
    suspend fun saveSession(user: UserModel) {
        dataStore.edit { pref ->
            pref[ID_KEY] = user.id
            pref[NAME_KEY] = user.name
            pref[TOKEN_KEY] = user.token
            pref[IS_LOGIN_KEY] = true.toString()
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { pref ->
            UserModel(
                pref[ID_KEY] ?: "",
                pref[NAME_KEY] ?: "",
                pref[TOKEN_KEY] ?: "",
                pref[IS_LOGIN_KEY].toBoolean(),
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { it.clear() }
    }

    companion object {
        private val ID_KEY = stringPreferencesKey("id")
        private val NAME_KEY = stringPreferencesKey("name")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = stringPreferencesKey("is_login_key")

        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserPreference(dataStore).also { INSTANCE = it }
            }
        }
    }
}