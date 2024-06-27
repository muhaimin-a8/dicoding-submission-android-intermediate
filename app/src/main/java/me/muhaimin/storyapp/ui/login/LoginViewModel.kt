package me.muhaimin.storyapp.ui.login

import androidx.lifecycle.ViewModel
import me.muhaimin.storyapp.data.UserRepository
import me.muhaimin.storyapp.data.dto.UserLoginRequest
import me.muhaimin.storyapp.data.dto.UserRegisterRequest

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun login(user: UserLoginRequest) = repository.login(user)
}