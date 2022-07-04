package com.shivamkumarjha.oneplus.model.product

import com.google.gson.annotations.SerializedName

data class OnePlusProduct(
    @SerializedName("hasStock") val hasStock: Boolean,
    @SerializedName("discountRate") val discountRate: Int,
    @SerializedName("productDisplayType") val productDisplayType: Int,
    @SerializedName("publishTime") val publishTime: Int,
    @SerializedName("canUseRedCoins") val canUseRedCoins: Boolean,
    @SerializedName("skuList") val productSku: ArrayList<ProductSku>?,
    @SerializedName("originalPrice") val originalPrice: Int,
    @SerializedName("salePrice") val salePrice: Int,
    @SerializedName("discountRange") val discountRange: Boolean,
    @SerializedName("originalPriceRange") val originalPriceRange: Boolean,
    @SerializedName("currencySymbol") val currencySymbol: String,
    @SerializedName("saleStatus") val saleStatus: Int,
    @SerializedName("primeImageUrl") val primeImageUrl: String,
    @SerializedName("hasGift") val hasGift: Boolean,
    @SerializedName("productName") val productName: String,
    @SerializedName("newProduct") val newProduct: Boolean,
    @SerializedName("topSet") val topSet: Boolean,
    @SerializedName("productDetailUrl") val productDetailUrl: String,
    @SerializedName("nowPrice") val nowPrice: Int,
    @SerializedName("productCode") val productCode: String, //primary key
    @SerializedName("nowPriceRange") val nowPriceRange: Boolean,
    @SerializedName("salePriceRange") val salePriceRange: Boolean
)