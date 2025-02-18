package io.maa96.ubar.data.repository

import io.maa96.ubar.data.api.UbarApi
import io.maa96.ubar.data.dto.Location
import io.maa96.ubar.data.mapper.LocationDtoMapper
import io.maa96.ubar.domain.model.Address
import io.maa96.ubar.domain.model.Resources
import io.maa96.ubar.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val api: UbarApi,
    private val locationDtoMapper: LocationDtoMapper
) : AddressRepository {
    override suspend fun postAddress(location: Location): Flow<Resources<Address>> {
        return flow {
            emit(Resources.Loading(true))
            try {
                val response = api.postAddress(location)
                val address = locationDtoMapper.toDomain(response)
                emit(Resources.Success(address))
                emit(Resources.Loading(false))

            } catch (e: Exception) {
                emit(Resources.Error("Could not load data"))
                emit(Resources.Loading(false))
            }
        }
    }

    override suspend fun getAddress(): Flow<Resources<List<Address>>> {
        return flow {
            emit(Resources.Loading(true))
            try {
                val response = api.getAddress().map {
                    locationDtoMapper.toDomain(it)
                }
                emit(Resources.Success(response))
            } catch (e: Exception) {
                emit(Resources.Error("Could not load data"))
                emit(Resources.Loading(false))
            }
        }
    }

}