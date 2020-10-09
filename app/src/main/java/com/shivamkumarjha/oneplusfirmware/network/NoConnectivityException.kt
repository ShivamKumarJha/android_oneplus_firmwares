package com.shivamkumarjha.oneplusfirmware.network

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String = "No Internet Connection!"
}