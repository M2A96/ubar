package io.maa96.ubar.data.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    val id: String,
    @SerializedName("address_id")
    val addressId: String,
    val region: Region,
    val address: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("first_name")
    val firstName: String,
    val gender: String,
    val lat: Double,
    val lng: Double,
    @SerializedName("coordinate_mobile")
    val coordinateMobile: String,
    @SerializedName("coordinate_phone_number")
    val coordinatePhoneNumber: String
)