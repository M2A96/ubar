package io.maa96.ubar.data.model

data class Location(
    val region: Int,
    val address: String,
    val lat: Double,
    val lng: Double,
    val coordinateMobile: String,
    val coordinatePhoneNumber: String,
    val firstName: String,
    val lastName: String,
    val gender: String
)
