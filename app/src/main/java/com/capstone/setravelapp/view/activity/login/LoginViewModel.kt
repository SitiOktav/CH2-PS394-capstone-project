package com.capstone.setravelapp.view.activity.login

import androidx.lifecycle.ViewModel
import com.capstone.setravelapp.data.PlaceRepository

class LoginViewModel(private val placeRepository: PlaceRepository) : ViewModel() {

    fun login(email: String, password: String) = placeRepository.login(email, password)

}