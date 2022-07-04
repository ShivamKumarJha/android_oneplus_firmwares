package com.shivamkumarjha.oneplus.model.firmware

import com.google.gson.annotations.SerializedName

data class Model(
    @SerializedName("phoneCode") val phoneCode: String,
    @SerializedName("phoneName") val phoneName: String,
    @SerializedName("phoneImage") val phoneImage: String
)