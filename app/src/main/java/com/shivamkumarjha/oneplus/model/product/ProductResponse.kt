package com.shivamkumarjha.oneplus.model.product

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("ret") val ret: Int,
    @SerializedName("errCode") val errCode: String?,
    @SerializedName("errMsg") val errMsg: String?,
    @SerializedName("data") val productData: ProductData?,
    @SerializedName("page") val page: String?
)