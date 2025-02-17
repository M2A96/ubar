package io.maa96.ubar.domain.usecase

import io.maa96.ubar.data.model.Location
import io.maa96.ubar.data.model.LocationResponse
import io.maa96.ubar.domain.model.Resources
import io.maa96.ubar.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostAddressUseCase @Inject constructor(
    private val addressRepository: AddressRepository
){
    suspend operator fun invoke(location: Location) : Flow<Resources<LocationResponse>> = addressRepository.postAddress(location)
}