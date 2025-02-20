package io.maa96.ubar.presentation.ui.list

import io.maa96.ubar.domain.model.Address

data class ListScreenState (
    val isLoading: Boolean = false,
    val valErrorMessage: String = "",
    val addresses: List<Address>? = emptyList()
)

