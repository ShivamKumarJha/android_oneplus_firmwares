package com.shivamkumarjha.oneplus.model.product

import com.google.gson.annotations.SerializedName

data class ProductSku(
    @SerializedName("hasStock") val hasStock: Boolean,
    @SerializedName("discountRate") val discountRate: Int,
    @SerializedName("publishTime") val publishTime: Int,
    @SerializedName("canUseRedCoins") val canUseRedCoins: Boolean,
    @SerializedName("color") val color: String?,
    @SerializedName("originalPrice") val originalPrice: Int,
    @SerializedName("bundleMinNowPrice") val bundleMinNowPrice: String?,
    @SerializedName("salePrice") val salePrice: Int,
    @SerializedName("length") val length: String?,
    @SerializedName("saleStatus") val saleStatus: Int,
    @SerializedName("primeImageUrl") val primeImageUrl: String,
    @SerializedName("hasGift") val hasGift: Boolean,
    @SerializedName("newProduct") val newProduct: Boolean,
    @SerializedName("skuName") val skuName: String,
    @SerializedName("nowPrice") val nowPrice: Int,
    @SerializedName("rom") val rom: String?,
    @SerializedName("bundleMaxNowPrice") val bundleMaxNowPrice: String?,
    @SerializedName("inPriceFilterRange") val inPriceFilterRange: String?,
    @SerializedName("size") val size: String?,
    @SerializedName("skuCode") val skuCode: Int //primary key
)