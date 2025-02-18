package io.maa96.ubar.presentation.ui.login

import io.maa96.ubar.data.dto.Location

sealed class LoginScreenEvent {
    data class OnNameChange(val name: String) : LoginScreenEvent()
    data class OnFamilyNameChange(val familyName: String) : LoginScreenEvent()
    data class OnMobilePhoneChange(val mobilePhone: String) : LoginScreenEvent()
    data class OnLandlinePhoneChange(val landlinePhone: String) : LoginScreenEvent()
    data class OnAddressChange(val address: String) : LoginScreenEvent()
    data class OnGenderChange(val gender: String) : LoginScreenEvent()
    data class OnNextButtonClick(val location: Location) : LoginScreenEvent()
}