package com.capstone.setravelapp.data.remote

import com.capstone.setravelapp.data.remote.response.FoodResponseItem
import com.capstone.setravelapp.data.remote.response.LoginResponse
import com.capstone.setravelapp.data.remote.response.PlaceResponse
import com.capstone.setravelapp.data.remote.response.PlaceResponseItem
import com.capstone.setravelapp.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String
    ): RegisterResponse

    @GET("food")
    fun getAllFood(): Call<List<FoodResponseItem>>

    @GET("place")
    fun getAllPlaces(): Call<List<PlaceResponseItem>>


}