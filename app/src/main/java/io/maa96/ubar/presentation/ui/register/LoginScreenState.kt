package io.maa96.ubar.presentation.ui.register

import io.maa96.ubar.data.dto.Location

data class LoginData(
    val name: String = "",
    val familyName: String = "",
    val mobilePhone: String = "",
    val landlinePhone: String = "",
    val address: String = "",
    val gender: Gender = Gender.MALE,

)

data class LoginScreenState (
    val location: Location = Location(),
    val name: String = "",
    val familyName: String = "",
    val mobilePhone: String = "",
    val landlinePhone: String = "",
    val address: String = "",
    val gender: Gender = Gender.MALE,
    val isLoading: Boolean = false,
    val error: String? = null
)


sealed class Gender(val gender: String) {
    data object MALE: Gender("MALE")
    data object FEMALE: Gender("FEMALE")
}