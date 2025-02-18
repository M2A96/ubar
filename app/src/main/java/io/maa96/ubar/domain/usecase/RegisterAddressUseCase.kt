package io.maa96.ubar.domain.usecase

import io.maa96.ubar.data.dto.Location
import io.maa96.ubar.domain.model.Address
import io.maa96.ubar.domain.model.Resources
import io.maa96.ubar.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterAddressUseCase @Inject constructor(
    private val addressRepository: AddressRepository
){
    suspend operator fun invoke(location: Location) : Flow<Resources<Address>> = addressRepository.postAddress(location)
}