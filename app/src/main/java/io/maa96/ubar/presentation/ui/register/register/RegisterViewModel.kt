package io.maa96.ubar.presentation.ui.register.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maa96.ubar.data.dto.Location
import io.maa96.ubar.domain.model.Resources
import io.maa96.ubar.domain.usecase.RegisterAddressUseCase
import io.maa96.ubar.util.Validator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerAddressUseCase: RegisterAddressUseCase,
    private val validator: Validator
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationUiState())
    val state = _state.asStateFlow()

    fun processIntent(intent: RegistrationIntent) {
        when (intent) {
            is RegistrationIntent.UpdateFirstName -> {
                _state.update { currentState ->
                    currentState.copy(
                        firstName = intent.firstName,
                        isFirstNameValid = validator.isFirstNameValid(intent.firstName)
                    )
                }
            }

            is RegistrationIntent.UpdateLastName -> {
                _state.update { currentState ->
                    currentState.copy(
                        lastName = intent.lastName,
                        isLastNameValid = validator.isLastNameValid(intent.lastName)
                    )
                }
            }

            is RegistrationIntent.UpdateMobile -> {
                _state.update { currentState ->
                    currentState.copy(
                        mobile = intent.mobile,
                        isMobileValid = validator.isPhoneNumberValid(intent.mobile)
                    )
                }
            }

            is RegistrationIntent.UpdatePhone -> {
                _state.update { currentState ->
                    currentState.copy(
                        phone = intent.phone,
                        isPhoneValid = validator.isPhoneNumberValid(intent.phone)
                    )
                }
            }

            is RegistrationIntent.UpdateAddress -> {
                _state.update { currentState ->
                    currentState.copy(
                        address = intent.address,
                        isAddressValid = validator.validateStringNotEmpty(intent.address)
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

            RegistrationIntent.ResetState -> {
                resetState()
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
                                error = null,
                                isSubmissionSuccessful = true
                            )
                        }
                    }
                    is Resources.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message,
                                isSubmissionSuccessful = false
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

    private fun resetState() {
        _state.update {
            RegistrationUiState()
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
                state.isPhoneValid &&
                state.isAddressValid &&
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
    val isPhoneValid: Boolean = false,
    val address: String = "",
    val isAddressValid: Boolean = false,
    val gender: Genders = Genders.MALE,
    val location: LatLng? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSubmissionSuccessful: Boolean = false

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
    data object ResetState : RegistrationIntent
}