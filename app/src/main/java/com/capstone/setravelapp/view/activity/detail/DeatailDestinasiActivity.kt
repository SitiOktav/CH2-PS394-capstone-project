package com.capstone.setravelapp.view.activity.detail

import CrimePredictor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.capstone.setravelapp.R
import com.capstone.setravelapp.data.remote.response.PlaceResponseItem
import com.capstone.setravelapp.databinding.ActivityDeatailDestinasiBinding
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
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

        // Mendapatkan data tempat dari intent
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
                // Memanggil metode untuk menampilkan tingkat kejahatan
                placeItem?.let { displayCrimeRate(it) }
            }
        }

        // Inisialisasi CrimePredictor
        crimePredictor = CrimePredictor(this)


    }

    private fun displayCrimeRate(placeItem: PlaceResponseItem) {
        if (!::crimePredictor.isInitialized) {
            crimePredictor = CrimePredictor(this)
        }

        // Ambil data jumlah penduduk, jumlah crime, dan crime per kapita dari PlaceResponseItem
        val jumlahPenduduk = placeItem.jumlahPenduduk?.toDouble() ?: 0.0
        val jumlahCrime = placeItem.jumlahCrime?.toDouble() ?: 0.0
        val crimePerCapita = placeItem.crimePerCapita?.toDouble() ?: 0.0

        // Preprocessing data untuk dimasukkan ke CrimePredictor
        val inputData = crimePredictor.preprocessData(jumlahPenduduk, jumlahCrime, crimePerCapita)

        // Prediksi tingkat kejahatan
        val crimeRate = crimePredictor.predictCrimeRate(inputData)


        // Menampilkan hasil prediksi di Pie Chart
        val totalPercentage = 100.0
        val pieChart1 = findViewById<PieChart>(R.id.chart_crime)
        pieChart1.clearChart()

        // Menyesuaikan nilai crimeRate menjadi persentase
        val crimeRatePercentage = (crimeRate / totalPercentage).toFloat()

        // Menampilkan hasil prediksi di tv_crime
        val formattedCrimeRate = String.format("%.2f", crimeRate)
        binding.tvDataCrime.text = formattedCrimeRate

        val crimeCategory = when {
            crimeRate < 20 -> "Low"
            crimeRate >= 20 && crimeRate <= 50 -> "Medium"
            else -> "High"
        }


        val pieChart = findViewById<PieChart>(R.id.chart_crime)
        pieChart.clearChart()
        when (crimeCategory) {
            "Low" -> {
                pieChart.addPieSlice(
                    PieModel(
                        "Crime Rate",
                        crimeRatePercentage.toFloat(),
                        resources.getColor(R.color.green)
                    )
                )
            }

            "Medium" -> {
                pieChart.addPieSlice(
                    PieModel(
                        "Crime Rate",
                        crimeRatePercentage.toFloat(),
                        resources.getColor(R.color.yellow)
                    )
                )
            }

            "High" -> {
                pieChart.addPieSlice(
                    PieModel(
                        "Crime Rate",
                        crimeRatePercentage.toFloat(),
                        resources.getColor(R.color.red)
                    )
                )
            }
        }

        // Menampilkan persentase di tengah pie chart
        pieChart.startAnimation()
    }
}