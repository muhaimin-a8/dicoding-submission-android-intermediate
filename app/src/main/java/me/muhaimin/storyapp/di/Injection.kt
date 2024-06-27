package me.muhaimin.storyapp.di

import android.content.Context
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import me.muhaimin.storyapp.data.UserRepository
import me.muhaimin.storyapp.data.api.ApiConfig
import me.muhaimin.storyapp.data.pref.UserPreference
import me.muhaimin.storyapp.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(pref, apiService)
    }
}