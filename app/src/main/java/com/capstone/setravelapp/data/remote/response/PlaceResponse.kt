package com.capstone.setravelapp.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName



@Parcelize
data class PlaceResponse(

	@field:SerializedName("PlaceResponse")
	val placeResponse: List<PlaceResponseItem?>?
) : Parcelable

@Parcelize
@Entity(tableName = "PlaceResponseItem")
data class PlaceResponseItem(

	@field:SerializedName("Description")
	val description: String?,

	@field:SerializedName("Category")
	val category: String?,

	@field:SerializedName("Address")
	val address: String?,

	@PrimaryKey
	@NonNull
	@field:SerializedName("Place_Name")
	val placeName: String,

	@field:SerializedName("Price")
	val price: String?,

	@field:SerializedName("Rating")
	val rating: String?,

	@field:SerializedName("City")
	val city: String?,

	@field:SerializedName("Potensi_bencana")
	val potensiBencana: String?,

	@field:SerializedName("Place_Image")
	val placeImage: String?,

	@field:SerializedName("Location")
	val location: String?,

	@field:SerializedName("Jumlah_Penduduk")
	val jumlahPenduduk: Int?,

	@field:SerializedName("Jumlah_Crime")
	val jumlahCrime: Int?,

	@field:SerializedName("Crime_per_Capita")
	val crimePerCapita: String?,
) : Parcelable
