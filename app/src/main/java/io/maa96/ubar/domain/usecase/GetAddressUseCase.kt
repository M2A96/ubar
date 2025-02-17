package io.maa96.ubar.domain.usecase

import io.maa96.ubar.domain.repository.AddressRepository
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(
    private val addressRepository: AddressRepository
) {
    suspend operator fun invoke() = addressRepository.getAddress()

}