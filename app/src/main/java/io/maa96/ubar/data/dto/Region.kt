package io.maa96.ubar.data.model

import com.google.gson.annotations.SerializedName

data class Region(
    val id: Int,
    val name: String,
    @SerializedName("city_object")
    val cityObject: City,
    @SerializedName("state_object")
    val stateObject: State
)

