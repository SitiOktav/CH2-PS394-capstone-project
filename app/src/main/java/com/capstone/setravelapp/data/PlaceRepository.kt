package com.capstone.setravelapp.data

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.setravelapp.data.remote.ApiService
import com.capstone.setravelapp.data.remote.response.LoginResponse
import com.capstone.setravelapp.data.remote.response.RegisterResponse
import java.lang.Exception

class PlaceRepository(
    private val apiService: ApiService,
) {

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.d(ContentValues.TAG, "onFailure: ${e.message.toString()}")
        }
    }


    fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(username, email, password, confirmPassword)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "onFailure: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: PlaceRepository? = null

        fun getInstance(apiService: ApiService): PlaceRepository =
            instance ?: synchronized(this) {
                instance ?: PlaceRepository(apiService)
            }.also { instance = it }
    }

}
