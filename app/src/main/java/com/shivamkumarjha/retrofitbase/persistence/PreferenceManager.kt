package com.shivamkumarjha.retrofitbase.persistence

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val pref: SharedPreferences
    private var privateMode = 0

    companion object {
        private var INSTANCE: PreferenceManager? = null

        // Shared pref file name
        private const val PREF_NAME = "Preferences"

        fun initialize(context: Context): PreferenceManager? {
            if (INSTANCE == null) {
                INSTANCE = PreferenceManager(context)
            }
            return INSTANCE
        }

        fun get(): PreferenceManager {
            return if (INSTANCE != null) {
                INSTANCE!!
            } else {
                throw IllegalStateException("Please initialize PreferenceManager before getting the instance!")
            }
        }
    }

    init {
        pref = context.getSharedPreferences(PREF_NAME, privateMode)
    }
}
