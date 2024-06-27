package me.muhaimin.storyapp.data.dto

import com.google.gson.annotations.SerializedName

data class UserLoginRequest(

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String
)