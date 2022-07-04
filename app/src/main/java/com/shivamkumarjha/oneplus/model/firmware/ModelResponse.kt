package com.shivamkumarjha.oneplus.model.firmware

import com.google.gson.annotations.SerializedName

data class ModelResponse(
    @SerializedName("ret") val ret: Int,
    @SerializedName("errCode") val errCode: Int?,
    @SerializedName("errMsg") val errMsg: String?,
    @SerializedName("data") val models: List<Model>?,
    @SerializedName("traceId") val traceId: String?
)