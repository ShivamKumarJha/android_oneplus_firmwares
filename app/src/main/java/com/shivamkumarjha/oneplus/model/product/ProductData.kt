package com.shivamkumarjha.oneplus.model.product

import com.google.gson.annotations.SerializedName

data class ProductData(
    @SerializedName("recommendProductList") val recommendProductList: String?,
    @SerializedName("productList") val onePlusProduct: ArrayList<OnePlusProduct>?
)