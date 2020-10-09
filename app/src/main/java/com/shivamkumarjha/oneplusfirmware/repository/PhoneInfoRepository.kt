package com.shivamkumarjha.oneplusfirmware.repository

import com.shivamkumarjha.oneplusfirmware.model.PhoneInfo
import com.shivamkumarjha.oneplusfirmware.network.ApiListener
import com.shivamkumarjha.oneplusfirmware.network.ApiService
import com.shivamkumarjha.oneplusfirmware.network.NoConnectivityException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PhoneInfoRepository {

    var phoneInfo: PhoneInfo? = null

    suspend fun getPhoneInfo(storeCode: String, phoneCode: String, apiListener: ApiListener) =
        withContext(Dispatchers.Default) {
            ApiService.create().getPhoneInfo(storeCode, phoneCode)
                .enqueue(object : Callback<PhoneInfo> {
                    override fun onResponse(call: Call<PhoneInfo>, response: Response<PhoneInfo>?) {
                        if (response != null) {
                            if (response.code() == 200) {
                                phoneInfo = response.body()
                                apiListener.onResponse(response.body())
                            } else
                                apiListener.onResponseError(response.code())
                        }
                    }

                    override fun onFailure(call: Call<PhoneInfo>, t: Throwable?) {
                        if (t is NoConnectivityException) {
                            apiListener.onOffline(t.message)
                        } else {
                            apiListener.onFailure(t?.localizedMessage.toString())
                        }
                    }
                })
        }

}