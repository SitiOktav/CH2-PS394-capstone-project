package com.capstone.setravelapp.view.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.setravelapp.data.remote.response.FoodResponseItem
import com.capstone.setravelapp.data.remote.ApiService
import com.capstone.setravelapp.data.remote.response.PlaceResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val ApiService: ApiService) : ViewModel() {

    private val _foodList = MutableLiveData<List<FoodResponseItem>>()
    val foodList: LiveData<List<FoodResponseItem>> get() = _foodList

    //destinasi
    private val _placeList = MutableLiveData<List<PlaceResponseItem>>()
    val placeList: LiveData<List<PlaceResponseItem>> get() = _placeList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun getFoodList() {
        ApiService.getAllFood().enqueue(object : Callback<List<FoodResponseItem>> {
            override fun onResponse(
                call: Call<List<FoodResponseItem>>,
                response: Response<List<FoodResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _foodList.value = response.body()
                } else {
                    // Handle response failure
                }
            }

            override fun onFailure(call: Call<List<FoodResponseItem>>, t: Throwable) {
                // Handle network failure
            }
        })

    }

    //destinasi
    fun getPlaceList() {
        _loading.value = true

        ApiService.getAllPlaces().enqueue(object : Callback<List<PlaceResponseItem>> {
            override fun onResponse(
                call: Call<List<PlaceResponseItem>>,
                response: Response<List<PlaceResponseItem>>
            ) {
                _loading.value = false

                if (response.isSuccessful) {
                    _placeList.value = response.body()
                    } else {

                }
            }

            override fun onFailure(call: Call<List<PlaceResponseItem>>, t: Throwable) {
                _loading.value = false
                // Handle network failure
            }
        })
    }

}
