package com.akki.domain.enitity

import com.google.gson.annotations.SerializedName

data class ScanResult(
    @SerializedName("price_per_min")
    val pricePerMin: Double? = 0.0,
    @SerializedName(value = "location_details", alternate = ["location_detail"])
    val locationDetails: String? = "",
    @SerializedName("location_id")
    val locationId: String? = "",
    var totalMin: Long = 0L,
    var totalPrice: Float = 0F,
    var isInValidQrCode: Boolean = false
)
