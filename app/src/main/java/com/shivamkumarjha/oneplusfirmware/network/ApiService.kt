package com.shivamkumarjha.oneplusfirmware.network

import com.shivamkumarjha.oneplusfirmware.model.OneplusPhones
import com.shivamkumarjha.oneplusfirmware.model.PhoneInfo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET

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

    @GET("find-phone-models")
    fun getPhoneModels(): Call<OneplusPhones>

    @GET("find-phone-systems")
    fun getPhoneInfo(): Call<PhoneInfo>
}