package io.maa96.ubar.presentation.ui.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maa96.ubar.data.dto.Location
import io.maa96.ubar.domain.usecase.RegisterAddressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val registerAddressUseCase: RegisterAddressUseCase
): ViewModel() {

    private val _state = MutableStateFlow(LoginScreenState())
    val state = _state.asStateFlow()

    init {
        registerAddress(Location(
            region = 1,
            address = "sample address",
            lat = 35.7717503,
            lng = 51.3365315,
            coordinateMobile = "09330192942",
            coordinatePhoneNumber = "",
            firstName = "sample",
            lastName = "name",
            gender = "MALE"
        ))
    }

    fun onEvent(event: LoginScreenEvent) {
        when(event) {
            is LoginScreenEvent.OnNameChange -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }
            is LoginScreenEvent.OnFamilyNameChange -> {
                _state.update {
                    it.copy(
                        familyName = event.familyName
                    )
                }
            }
            is LoginScreenEvent.OnMobilePhoneChange -> {
                _state.update {
                    it.copy(
                        mobilePhone = event.mobilePhone
                    )
                }
            }

            is LoginScreenEvent.OnAddressChange -> {
                _state.update {
                    it.copy(
                        address = event.address
                    )
                }
            }
            is LoginScreenEvent.OnGenderChange -> {
                updateLocation(Location(
                    gender = event.gender
                ))
            }
            is LoginScreenEvent.OnLandlinePhoneChange -> {
                updateLocation(Location(
                    coordinatePhoneNumber = event.landlinePhone
                ))
            }
            is LoginScreenEvent.OnNextButtonClick -> {
                registerAddress(location = event.location)
            }
        }
    }

    private fun updateLocation(value: Location) {
        _state.value = _state.value.copy(
            isLoading = false,
            location = value
        )
    }
    private fun registerAddress(location: Location) {
        viewModelScope.launch {
            registerAddressUseCase(location)
                .onStart {  }
                .catch {  }
                .collect {
                    Log.d("LoginScreenViewModel", "registerAddress: ${it.data}")
                }
        }
    }

}