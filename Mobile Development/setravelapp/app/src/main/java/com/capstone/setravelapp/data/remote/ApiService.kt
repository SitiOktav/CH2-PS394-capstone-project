package com.capstone.setravelapp.data.remote

import com.capstone.setravelapp.data.remote.response.FoodResponseItem
import com.capstone.setravelapp.data.remote.response.PlaceResponse
import com.capstone.setravelapp.data.remote.response.PlaceResponseItem
import retrofit2.Call
import retrofit2.http.GET


interface ApiService {

    @GET("food")
    fun getAllFood(): Call<List<FoodResponseItem>>

    @GET("place")
    fun getAllPlaces(): Call<List<PlaceResponseItem>>


}