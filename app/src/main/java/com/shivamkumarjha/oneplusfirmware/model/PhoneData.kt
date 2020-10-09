package com.shivamkumarjha.oneplusfirmware.model

import com.google.gson.annotations.SerializedName

data class PhoneData(
    @SerializedName("phoneCode") val phoneCode: String,
    @SerializedName("phoneName") val phoneName: String,
    @SerializedName("phoneImage") val phoneImage: String
)