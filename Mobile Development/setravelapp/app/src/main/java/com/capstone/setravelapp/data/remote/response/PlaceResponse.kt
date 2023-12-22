package com.capstone.setravelapp.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PlaceResponse(

	@field:SerializedName("PlaceResponse")
	val placeResponse: List<PlaceResponseItem?>? = null
) : Parcelable

@Parcelize
data class PlaceResponseItem(

	@field:SerializedName("Description")
	val description: String? = null,

	@field:SerializedName("Category")
	val category: String? = null,

	@field:SerializedName("Address")
	val address: String? = null,

	@field:SerializedName("Place_Name")
	val placeName: String? = null,

	@field:SerializedName("Price")
	val price: String? = null,

	@field:SerializedName("Rating")
	val rating: String? = null,

	@field:SerializedName("City")
	val city: String? = null,

	@field:SerializedName("Potensi_bencana")
	val potensiBencana: String? = null,

	@field:SerializedName("Place_Image")
	val placeImage: String? = null,

	@field:SerializedName("Location")
	val location: String? = null
) : Parcelable
