package com.capstone.setravelapp.view.activity.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.setravelapp.data.PlaceRepository
import com.capstone.setravelapp.data.Result
import com.capstone.setravelapp.data.remote.response.RegisterResponse

class RegisterViewModel(private val repository: PlaceRepository) : ViewModel() {

    fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): LiveData<Result<RegisterResponse>> =
        repository.register(username, email, password, confirmPassword)

}
