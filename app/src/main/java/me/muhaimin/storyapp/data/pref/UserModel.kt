package me.muhaimin.storyapp.data.pref

data class UserModel(
    val id: String,
    val name: String,
    val token: String,
    val isLogin: Boolean = false,
)