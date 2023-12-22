package com.capstone.setravelapp.view.activity.detailmakanan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.capstone.setravelapp.data.remote.response.FoodResponseItem
import com.capstone.setravelapp.databinding.ActivityDetailMakananBinding

@Suppress("DEPRECATION")
class DetailMakananActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_FOOD_ITEM = "extra_food_item"
    }

    private lateinit var binding: ActivityDetailMakananBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMakananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan data makanan dari intent
        val foodItem = intent.getParcelableExtra<FoodResponseItem>(EXTRA_FOOD_ITEM)

        // Menampilkan data makanan di dalam tampilan
        foodItem?.let {
            binding.apply {
                tvName.text = it.namaKuliner
                tvDecription.text = it.deskripsi
                tvAlamatmakan.text = it.asalKota
                Glide.with(this@DetailMakananActivity)
                    .load(it.imageUrl)
                    .centerCrop()
                    .into(ivFotomakan)
            }
        }
    }
}