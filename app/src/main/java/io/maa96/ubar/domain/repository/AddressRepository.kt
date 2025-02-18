package io.maa96.ubar.domain.repository

import io.maa96.ubar.data.dto.Location
import io.maa96.ubar.domain.model.Address
import io.maa96.ubar.domain.model.Resources
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    suspend fun postAddress(location: Location): Flow<Resources<Address>>
    suspend fun getAddress(): Flow<Resources<List<Address>>>

}