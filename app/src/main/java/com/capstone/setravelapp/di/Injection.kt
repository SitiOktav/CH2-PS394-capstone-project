package com.capstone.setravelapp.di

import android.content.Context
import com.capstone.setravelapp.data.remote.ApiConfig
import com.capstone.setravelapp.data.PlaceRepository
import com.capstone.setravelapp.data.local.room.PlaceDatabase
import com.capstone.setravelapp.view.activity.signup.RegisterViewModelFactory

object Injection {
    fun provideRepository(context: Context): PlaceRepository {
        val apiService = ApiConfig.getApiService()
        val database = PlaceDatabase.getInstance(context)
        return PlaceRepository.getInstance(apiService)
    }
}
