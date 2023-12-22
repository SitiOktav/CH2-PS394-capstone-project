package com.capstone.setravelapp.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class FoodResponse(

	@field:SerializedName("FoodResponse")
	val foodResponse: List<FoodResponseItem?>?
) : Parcelable

@Parcelize
data class FoodResponseItem(

	@field:SerializedName("image_url")
	val imageUrl: String?,

	@field:SerializedName("nama_kuliner")
	val namaKuliner: String?,

	@field:SerializedName("jenis")
	val jenis: String?,

	@field:SerializedName("asal_kota")
	val asalKota: String?,

	@field:SerializedName("deskripsi")
	val deskripsi: String?
) : Parcelable
