package com.capstone.setravelapp.view.fragment.home

import PlaceAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.setravelapp.adapter.FoodAdapter
import com.capstone.setravelapp.data.remote.ApiConfig
import com.capstone.setravelapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModelFactory: HomeViewModelFactory

    private lateinit var foodAdapter: FoodAdapter

    private lateinit var placeAdapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menggunakan HomeViewModelFactory untuk membuat instance HomeViewModel
        val apiService = ApiConfig.getApiService()
        viewModelFactory = HomeViewModelFactory(apiService)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        //makanan
        foodAdapter = FoodAdapter()
        binding.rvFood.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
            adapter = foodAdapter
        }

        //destinasi
        placeAdapter = PlaceAdapter()
        binding.rvPlace.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = placeAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.foodList.observe(viewLifecycleOwner, Observer {
            foodAdapter.submitList(it)
        })

        viewModel.getFoodList()

        //destinasi
        viewModel.placeList.observe(viewLifecycleOwner, Observer { placeList ->
            // Pastikan placeList tidak null sebelum digunakan
            placeList?.let {
                placeAdapter.submitList(it)
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.getPlaceList()
    }




    // Metode preprocessing
    fun preprocessInputData(a: Double, b: Double, c: Double): DoubleArray {
        val meanJSON = "[56938.97685749087, 59.11205846528624, 1.0930643311928245]"
        val scaleJSON = "[39000.359331530555, 175.84470812943704, 2.135285193333905]"

        val meanArray: DoubleArray = meanJSON
            .trim('[', ']')
            .split(",")
            .map { it.trim().toDouble() }
            .toDoubleArray()

        val scaleArray: DoubleArray = scaleJSON
            .trim('[', ']')
            .split(",")
            .map { it.trim().toDouble() }
            .toDoubleArray()

        val scaledA = (a - meanArray[0]) / scaleArray[0]
        val scaledB = (b - meanArray[1]) / scaleArray[1]
        val scaledC = (c - meanArray[2]) / scaleArray[2]

        return doubleArrayOf(scaledA, scaledB, scaledC)
    }



}