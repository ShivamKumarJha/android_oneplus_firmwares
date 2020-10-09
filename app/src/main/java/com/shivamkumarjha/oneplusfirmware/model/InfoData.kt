package com.shivamkumarjha.oneplusfirmware.model

import com.google.gson.annotations.SerializedName

data class InfoData(
    @SerializedName("phoneCode") val phoneCode: String,
    @SerializedName("phoneName") val phoneName: String,
    @SerializedName("phoneImage") val phoneImage: String,
    @SerializedName("versionNo") val versionNo: String,
    @SerializedName("versionType") val versionType: String,
    @SerializedName("versionReleaseTime") val versionReleaseTime: String,
    @SerializedName("versionLog") val versionLog: String,
    @SerializedName("versionSize") val versionSize: String,
    @SerializedName("versionSign") val versionSign: String,
    @SerializedName("versionLink") val versionLink: String
)