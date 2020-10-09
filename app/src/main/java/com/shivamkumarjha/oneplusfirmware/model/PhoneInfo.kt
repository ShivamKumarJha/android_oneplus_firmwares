package com.shivamkumarjha.oneplusfirmware.model

import com.google.gson.annotations.SerializedName

data class PhoneInfo(
    @SerializedName("ret") val ret: Int,
    @SerializedName("errCode") val errCode: Int,
    @SerializedName("errMsg") val errMsg: String,
    @SerializedName("data") val data: ArrayList<InfoData>?
)