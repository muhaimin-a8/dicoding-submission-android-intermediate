package me.muhaimin.storyapp.data.api

import me.muhaimin.storyapp.data.dto.AddStoryResponse
import me.muhaimin.storyapp.data.dto.GetAllStoryResponse
import me.muhaimin.storyapp.data.dto.GetStoryByIdResponse
import me.muhaimin.storyapp.data.dto.UserLoginRequest
import me.muhaimin.storyapp.data.dto.UserLoginResponse
import me.muhaimin.storyapp.data.dto.UserRegisterRequest
import me.muhaimin.storyapp.data.dto.UserRegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("register")
    suspend fun registerUser(
        @Body user: UserRegisterRequest
    ): UserRegisterResponse

    @POST("login")
    suspend fun loginUser(
        @Body user: UserLoginRequest
    ): UserLoginResponse

    @GET("stories")
    suspend fun getAllStories(): GetAllStoryResponse

    @GET("stories/{id}")
    suspend fun getStoryById(
        @Path("id") id: String
    ): GetStoryByIdResponse

    @POST("stories")
    @Multipart
    suspend fun addStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): AddStoryResponse
}