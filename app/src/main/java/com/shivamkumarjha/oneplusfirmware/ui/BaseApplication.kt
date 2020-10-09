package com.shivamkumarjha.oneplusfirmware.ui

import android.app.Application
import com.facebook.stetho.Stetho
import com.shivamkumarjha.oneplusfirmware.BuildConfig
import com.shivamkumarjha.oneplusfirmware.config.Constants
import com.shivamkumarjha.oneplusfirmware.network.RetrofitClient
import com.shivamkumarjha.oneplusfirmware.persistence.PreferenceManager

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