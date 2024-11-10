package com.zs.ispalindrome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.ispalindrome.model.User

class SelectedViewModel: ViewModel() {
    private val _selectedUser = MutableLiveData<User>()
    val selectedUser: LiveData<User> get() = _selectedUser

    fun setSelectedUser( user: User){
        _selectedUser.value = user
    }
}