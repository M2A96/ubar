package io.maa96.ubar.domain.repository

import io.maa96.ubar.data.model.Location
import io.maa96.ubar.data.model.LocationResponse
import io.maa96.ubar.domain.model.Resources
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    suspend fun postAddress(location: Location): Flow<Resources<LocationResponse>>
    suspend fun getAddress(): Flow<Resources<List<LocationResponse>>>

}