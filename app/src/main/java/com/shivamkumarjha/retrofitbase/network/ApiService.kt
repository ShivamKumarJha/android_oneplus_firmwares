package com.shivamkumarjha.retrofitbase.network

import retrofit2.Retrofit

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
}