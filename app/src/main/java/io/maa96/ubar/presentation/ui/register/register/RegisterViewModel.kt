package io.maa96.ubar.presentation.ui.register.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maa96.ubar.data.dto.Location
import io.maa96.ubar.domain.model.Resources
import io.maa96.ubar.domain.usecase.RegisterAddressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerAddressUseCase: RegisterAddressUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationUiState())
    val state = _state.asStateFlow()

    fun processIntent(intent: RegistrationIntent) {
        when (intent) {
            is RegistrationIntent.UpdateFirstName -> {
                _state.update { currentState ->
                    currentState.copy(
                        firstName = intent.firstName,
                        isFirstNameValid = intent.firstName.length >= 3
                    )
                }
            }

            is RegistrationIntent.UpdateLastName -> {
                _state.update { currentState ->
                    currentState.copy(
                        lastName = intent.lastName,
                        isLastNameValid = intent.lastName.length >= 3
                    )
                }
            }

            is RegistrationIntent.UpdateMobile -> {
                _state.update { currentState ->
                    currentState.copy(
                        mobile = intent.mobile,
                        isMobileValid = intent.mobile.matches(Regex("^09\\d{9}$"))
                    )
                }
            }

            is RegistrationIntent.UpdatePhone -> {
                _state.update { currentState ->
                    currentState.copy(
                        phone = intent.phone
                    )
                }
            }

            is RegistrationIntent.UpdateAddress -> {
                _state.update { currentState ->
                    currentState.copy(
                        address = intent.address
                    )
                }
            }

            is RegistrationIntent.UpdateGender -> {
                _state.update { currentState ->
                    currentState.copy(
                        gender = intent.gender
                    )
                }
            }

            is RegistrationIntent.UpdateLocation -> {
                _state.update { currentState ->
                    currentState.copy(
                        location = intent.location
                    )
                }
            }

            RegistrationIntent.Submit -> {
                submitRegistration()
            }

            RegistrationIntent.NavigateBack -> {
                // Handle navigation in the UI layer
            }
        }
    }

    private fun submitRegistration() {
        val currentState = _state.value
        if (!isValidRegistration(currentState)) {
            _state.update { it.copy(error = "Please fill all required fields correctly") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val location = currentState.toLocation()

            registerAddressUseCase(location).collect { result ->
                when (result) {
                    is Resources.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is Resources.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                    is Resources.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun RegistrationUiState.toLocation() = Location(
        region = 1,
        address = address,
        lat = location?.latitude ?: 0.0,
        lng = location?.longitude ?: 0.0,
        coordinateMobile = mobile,
        coordinatePhoneNumber = phone,
        firstName = firstName,
        lastName = lastName,
        gender = gender.toString()
    )

    private fun isValidRegistration(state: RegistrationUiState): Boolean {
        return state.isFirstNameValid &&
                state.isLastNameValid &&
                state.isMobileValid &&
                state.location != null &&
                state.address.isNotBlank()
    }
}

data class RegistrationUiState(
    val firstName: String = "",
    val isFirstNameValid: Boolean = false,
    val lastName: String = "",
    val isLastNameValid: Boolean = false,
    val mobile: String = "",
    val isMobileValid: Boolean = false,
    val phone: String = "",
    val address: String = "",
    val gender: Genders = Genders.MALE,
    val location: LatLng? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface RegistrationIntent {
    data class UpdateFirstName(val firstName: String) : RegistrationIntent
    data class UpdateLastName(val lastName: String) : RegistrationIntent
    data class UpdateMobile(val mobile: String) : RegistrationIntent
    data class UpdatePhone(val phone: String) : RegistrationIntent
    data class UpdateAddress(val address: String) : RegistrationIntent
    data class UpdateGender(val gender: Genders) : RegistrationIntent
    data class UpdateLocation(val location: LatLng) : RegistrationIntent
    data object Submit : RegistrationIntent
    data object NavigateBack : RegistrationIntent
}