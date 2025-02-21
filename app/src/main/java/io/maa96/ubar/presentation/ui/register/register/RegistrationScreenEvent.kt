package io.maa96.ubar.presentation.ui.register.register

import io.maa96.ubar.data.dto.Location

sealed class RegistrationScreenEvent {
    data class OnNameChange(val name: String) : RegistrationScreenEvent()
    data class OnFamilyNameChange(val familyName: String) : RegistrationScreenEvent()
    data class OnMobilePhoneChange(val mobilePhone: String) : RegistrationScreenEvent()
    data class OnLandlinePhoneChange(val landlinePhone: String) : RegistrationScreenEvent()
    data class OnAddressChange(val address: String) : RegistrationScreenEvent()
    data class OnGenderChange(val gender: String) : RegistrationScreenEvent()
    data class OnNextButtonClick(val location: Location) : RegistrationScreenEvent()
}