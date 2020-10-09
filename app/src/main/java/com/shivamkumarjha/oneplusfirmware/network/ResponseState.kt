package com.shivamkumarjha.oneplusfirmware.network

data class ResponseState(
    val isOffline: Any? = null,
    val responseCode: Any? = null,
    val errorMessage: Any? = null,
    val isSuccess: Boolean
)