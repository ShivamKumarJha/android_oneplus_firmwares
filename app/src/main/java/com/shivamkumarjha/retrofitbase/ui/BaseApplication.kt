package com.shivamkumarjha.retrofitbase.ui

import android.app.Application
import com.facebook.stetho.Stetho
import com.shivamkumarjha.retrofitbase.BuildConfig
import com.shivamkumarjha.retrofitbase.config.Constants
import com.shivamkumarjha.retrofitbase.network.RetrofitClient
import com.shivamkumarjha.retrofitbase.persistence.PreferenceManager

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PreferenceManager.initialize(applicationContext)
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(applicationContext)
        }
        RetrofitClient.initialize(applicationContext, Constants.BASE_URL)
    }
}