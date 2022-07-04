package com.shivamkumarjha.oneplus.model.firmware

import com.google.gson.annotations.SerializedName

data class System(
    @SerializedName("phoneCode") val phoneCode: String, //primary key
    @SerializedName("phoneName") val phoneName: String,
    @SerializedName("phoneImage") val phoneImage: String,
    @SerializedName("versionNo") val versionNo: String,
    @SerializedName("versionType") val versionType: Int,
    @SerializedName("versionReleaseTime") val versionReleaseTime: Int,
    @SerializedName("versionLog") val versionLog: String,
    @SerializedName("versionSize") val versionSize: String,
    @SerializedName("versionSign") val versionSign: String,
    @SerializedName("versionLink") val versionLink: String
)