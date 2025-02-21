package io.maa96.ubar.presentation.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.maa96.ubar.domain.model.Address
import io.maa96.ubar.domain.model.Resources
import io.maa96.ubar.domain.usecase.GetAddressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    val getAddressUseCase: GetAddressUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListScreenState())
    val state = _state.asStateFlow()


    init {
        onEvent(ListScreenEvent.OnGetAddressesResult)
    }

    private fun onEvent(event: ListScreenEvent) {
        when (event) {
            is ListScreenEvent.OnGetAddressesResult -> {
                getAddressList()
            }
        }
    }

    private fun getAddressList() {
        viewModelScope.launch {
            getAddressUseCase().onStart { setLoading(true) }
                .collect { result ->
                when (result) {
                    is Resources.Success -> {
                        setLoading(false)
                        result.data?.let { updateListScreenState(it) }
                    }
                    is Resources.Error -> {handleError()}
                    is Resources.Loading -> {setLoading(result.isLoading)}
                }
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    private fun handleError() {
        _state.update {
            it.copy(
                isLoading = false,
                addresses = null
            )
        }
    }
    private fun updateListScreenState(addresses: List<Address>) {
        _state.update { currentState ->
            currentState.copy(
                isLoading = false,
                addresses = addresses,
            )
        }
    }
}