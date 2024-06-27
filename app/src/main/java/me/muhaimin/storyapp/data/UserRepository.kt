package me.muhaimin.storyapp.data

import androidx.lifecycle.liveData
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import me.muhaimin.storyapp.data.api.ApiService
import me.muhaimin.storyapp.data.dto.GetAllStoryResponse
import me.muhaimin.storyapp.data.dto.UserLoginRequest
import me.muhaimin.storyapp.data.dto.UserLoginResponse
import me.muhaimin.storyapp.data.dto.UserRegisterRequest
import me.muhaimin.storyapp.data.dto.UserRegisterResponse
import me.muhaimin.storyapp.data.pref.UserModel
import me.muhaimin.storyapp.data.pref.UserPreference
import retrofit2.HttpException
import java.io.File

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun register(user: UserRegisterRequest) = liveData {
        emit(ResultState.Loading)
        try {
            val successResp = apiService.registerUser(user)
            emit(ResultState.Success(successResp))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val errorResp = Gson().fromJson(
                        e.response()?.errorBody().toString(), UserRegisterResponse::class.java
                    )
                    emit(ResultState.Error(errorResp.message))
                }
                else -> emit(ResultState.Error(e.message.toString()))
            }
        }
    }

    fun login(user: UserLoginRequest) = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.loginUser(user)
            val userModel = UserModel(
                id = response.loginResult?.userId.toString(),
                name = response.loginResult?.name.toString(),
                token = response.loginResult?.token.toString()
            )
            userPreference.saveSession(userModel)

            emit(ResultState.Success(response))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val errorResp = Gson().fromJson(
                        e.response()?.errorBody()?.string(), UserLoginResponse::class.java
                    )
                    emit(ResultState.Error(errorResp.message))
                }
                else -> emit(ResultState.Error(e.message.toString()))
            }
        }
    }

    fun getAllStories() = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getAllStories()
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val errorResp = Gson().fromJson(
                        e.response()?.errorBody()?.string(), UserLoginResponse::class.java
                    )
                    emit(ResultState.Error(errorResp.message.toString()))
                }
                else -> emit(ResultState.Error(e.message.toString()))
            }
        }
    }

    fun addStory(image: File, description: String) = liveData {
        emit(ResultState.Loading)
        val
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(pref: UserPreference, apiService: ApiService): UserRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(pref, apiService).also { INSTANCE = it }
            }
        }
    }
}