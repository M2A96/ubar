package io.maa96.ubar.presentation.ui.list

sealed class ListScreenEvent {
     data object OnGetAddressesResult: ListScreenEvent()
}