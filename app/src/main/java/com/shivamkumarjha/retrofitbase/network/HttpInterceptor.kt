package com.shivamkumarjha.retrofitbase.network

import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response

class HttpInterceptor(private val connectivityManager: ConnectivityManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isNetworkAvailable())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isNetworkAvailable(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
