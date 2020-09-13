package com.akki.domain.enitity

import com.google.gson.annotations.SerializedName

data class ScanResult(
    @SerializedName("price_per_min")
    val pricePerMin: Double? = 0.0,
    @SerializedName("location_details")
    val locationDetails: String? = "",
    @SerializedName("location_id")
    val locationId: String? = ""
)