package io.maa96.ubar.data.api

import io.maa96.ubar.data.model.Location
import io.maa96.ubar.data.model.LocationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UbarApi {

    @POST("address")
    suspend fun postAddress(
        @Body location: Location
    ): LocationResponse

    @GET("address")
    suspend fun getAddress(): List<LocationResponse>

}