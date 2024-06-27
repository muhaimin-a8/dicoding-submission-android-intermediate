package me.muhaimin.storyapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.muhaimin.storyapp.data.UserRepository
import me.muhaimin.storyapp.data.pref.UserModel

class HomeViewModel(private val repository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> = repository.getSession().asLiveData()

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getAllStories() = repository.getAllStories()
}