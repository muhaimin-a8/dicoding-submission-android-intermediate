package me.muhaimin.storyapp.ui.register

import androidx.lifecycle.ViewModel
import me.muhaimin.storyapp.data.UserRepository
import me.muhaimin.storyapp.data.dto.UserRegisterRequest

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    fun register(user: UserRegisterRequest) = repository.register(user)
}