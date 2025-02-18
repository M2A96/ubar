package io.maa96.ubar.data.dto

data class Location(
    val region: Int = 1,
    val address: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val coordinateMobile: String = "",
    val coordinatePhoneNumber: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val gender: String = ""
)
