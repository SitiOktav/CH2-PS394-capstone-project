package com.capstone.setravelapp.view.activity.detail

import CrimePredictor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.capstone.setravelapp.data.remote.response.PlaceResponseItem
import com.capstone.setravelapp.databinding.ActivityDeatailDestinasiBinding
import java.text.NumberFormat
import java.util.Currency

@Suppress("DEPRECATION")
class DeatailDestinasiActivity : AppCompatActivity() {

    //ml

    private lateinit var crimePredictor: CrimePredictor

    companion object {
        const val EXTRA_PLACE_ITEM = "extra_place_item"
    }

    private lateinit var binding: ActivityDeatailDestinasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeatailDestinasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan data makanan dari intent
        val placeItem = intent.getParcelableExtra<PlaceResponseItem>(EXTRA_PLACE_ITEM)

        // Menampilkan data makanan di dalam tampilan
        placeItem?.let {
            binding.apply {
                tvName.text = it.placeName
                tvAddress.text = it.address
                tvDecription.text = it.description
                tvCategori.text = it.category
                tvRating.text = it.rating

                // Format harga menjadi mata uang
                val price = placeItem.price?.toDoubleOrNull()
                if (price != null) {
                    val currencyFormat = NumberFormat.getCurrencyInstance()
                    currencyFormat.currency = Currency.getInstance("IDR")
                    tvPrice.text = currencyFormat.format(price)
                } else {
                    tvPrice.text = placeItem.price
                }

                tvBencana.text = it.potensiBencana
                Glide.with(this@DeatailDestinasiActivity)
                    .load(it.placeImage)
                    .centerCrop()
                    .into(ivPhoto)
            }
        }

        // Inisialisasi CrimePredictor
        crimePredictor = CrimePredictor(this)

        // Memanggil metode untuk menampilkan tingkat kejahatan
        displayCrimeRate()
    }

    private fun displayCrimeRate() {
        // Ambil data jumlah penduduk, jumlah crime, dan crime per kapita dari sumber data Anda
        val jumlahPenduduk = 14751.0
        val jumlahCrime = 44.0
        val crimePerCapita = 2.98284862

        // Preprocessing data untuk dimasukkan ke CrimePredictor
        val inputData = crimePredictor.preprocessData(jumlahPenduduk, jumlahCrime, crimePerCapita)

        // Prediksi tingkat kejahatan
        val crimeRate = crimePredictor.predictCrimeRate(inputData)

        // Menampilkan hasil prediksi di tv_crime
        val formattedCrimeRate = String.format("%.2f", crimeRate)
        binding.tvDataCrime.text = formattedCrimeRate
    }
}