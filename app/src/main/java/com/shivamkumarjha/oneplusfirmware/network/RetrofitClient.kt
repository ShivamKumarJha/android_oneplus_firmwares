package com.shivamkumarjha.oneplusfirmware.network

import android.content.Context
import android.net.ConnectivityManager
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shivamkumarjha.oneplusfirmware.BuildConfig
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient private constructor(
    private var mContext: Context,
    private var mBaseUrl: String
) {
    var retrofit: Retrofit

    companion object {
        private var INSTANCE: RetrofitClient? = null
        fun get(): RetrofitClient {
            return if (INSTANCE != null) {
                INSTANCE!!
            } else {
                throw IllegalStateException("Retrofit client not initialized.")
            }
        }

        fun initialize(context: Context, baseUrl: String) {
            INSTANCE = RetrofitClient(context, baseUrl)
        }
    }

    init {
        retrofit = getRetrofit(gson, getOkHTTPClient(getHttpCache(mContext)))
    }

    private fun getHttpCache(appContext: Context): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(appContext.cacheDir, cacheSize.toLong())
    }

    //setLenient() : By default, Gson is strict and only accepts JSON as specified by.
    //This option makes the parser liberal in what it accepts.
    private val gson: Gson
        get() {
            //setLenient() : By default, Gson is strict and only accepts JSON as specified by.
            //This option makes the parser liberal in what it accepts.
            val gsonBuilder = GsonBuilder().setLenient()
            gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            return gsonBuilder.create()
        }

    private fun getOkHTTPClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        val connectivityManager =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // set your desired log level
        if (BuildConfig.DEBUG) {
            // development build
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            // production build
            logging.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        client.connectTimeout(5, TimeUnit.MINUTES)
        client.readTimeout(5, TimeUnit.MINUTES)
        // add logging as last interceptor
        client.addInterceptor(logging)
        client.addInterceptor(HttpInterceptor(connectivityManager))
        client.cache(cache)
        client.addNetworkInterceptor(StethoInterceptor())
        client.retryOnConnectionFailure(true)
        client.connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
        return client.build()
    }

    private fun getRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(mBaseUrl)
            .client(okHttpClient)
            .build()
    }
}
