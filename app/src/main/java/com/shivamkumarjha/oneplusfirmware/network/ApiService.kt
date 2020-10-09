package com.shivamkumarjha.oneplusfirmware.network

import com.shivamkumarjha.oneplusfirmware.model.OneplusPhones
import com.shivamkumarjha.oneplusfirmware.model.PhoneInfo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    companion object {
        private var INSTANCE: ApiService? = null

        fun create(): ApiService {
            if (INSTANCE != null)
                return INSTANCE!!
            val retrofit: Retrofit = RetrofitClient.get().retrofit
            INSTANCE = retrofit.create(ApiService::class.java)
            return INSTANCE!!
        }
    }

    @FormUrlEncoded
    @POST("find-phone-models")
    fun getPhoneModels(@Field("storeCode") storeCode: String): Call<OneplusPhones>

    @FormUrlEncoded
    @POST("find-phone-systems")
    fun getPhoneInfo(
        @Field("storeCode") storeCode: String,
        @Field("phoneCode") phoneCode: String
    ): Call<PhoneInfo>
}