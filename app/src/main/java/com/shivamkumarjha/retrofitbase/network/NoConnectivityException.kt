package com.shivamkumarjha.retrofitbase.network

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String = "No Internet Connection!"
}