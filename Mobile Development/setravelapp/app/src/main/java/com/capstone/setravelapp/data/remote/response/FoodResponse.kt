package com.capstone.setravelapp.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class FoodResponse(

	@field:SerializedName("FoodResponse")
	val foodResponse: List<FoodResponseItem?>? = null
) : Parcelable

@Parcelize
data class FoodResponseItem(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("nama_kuliner")
	val namaKuliner: String? = null,

	@field:SerializedName("jenis")
	val jenis: String? = null,

	@field:SerializedName("asal_kota")
	val asalKota: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null
) : Parcelable
