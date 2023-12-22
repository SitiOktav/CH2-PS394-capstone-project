package com.capstone.setravelapp.view.activity.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.setravelapp.data.PlaceRepository
import com.capstone.setravelapp.di.Injection

class LoginViewModelFactory private constructor(private val storyRepository: PlaceRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(storyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }

    companion object {
        private var instance: LoginViewModelFactory? = null

        fun getInstance(context: Context): LoginViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: LoginViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}
