package io.maa96.ubar.data.mapper

import io.maa96.ubar.data.model.LocationResponse
import io.maa96.ubar.domain.model.Address
import javax.inject.Inject

class LocationDtoMapper @Inject constructor(){
    fun toDomain(dto: LocationResponse) = Address(
        address = dto.address,
        coordinateMobile = dto.coordinateMobile,
        firstNameLastName = dto.firstName + " " + dto.lastName
    )
}