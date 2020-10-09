package com.shivamkumarjha.oneplusfirmware.repository

import com.shivamkumarjha.oneplusfirmware.model.OneplusPhones
import com.shivamkumarjha.oneplusfirmware.network.ApiListener
import com.shivamkumarjha.oneplusfirmware.network.ApiService
import com.shivamkumarjha.oneplusfirmware.network.NoConnectivityException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PhoneModelsRepository {

    var oneplusPhones: OneplusPhones? = null

    suspend fun getOneplusPhones(storeCode: String, apiListener: ApiListener) =
        withContext(Dispatchers.Default) {
            ApiService.create().getPhoneModels(storeCode).enqueue(object : Callback<OneplusPhones> {
                override fun onResponse(
                    call: Call<OneplusPhones>,
                    response: Response<OneplusPhones>?
                ) {
                    if (response != null) {
                        if (response.code() == 200) {
                            oneplusPhones = response.body()
                            apiListener.onResponse(response.body())
                        } else
                            apiListener.onResponseError(response.code())
                    }
                }

                override fun onFailure(call: Call<OneplusPhones>, t: Throwable?) {
                    if (t is NoConnectivityException) {
                        apiListener.onOffline(t.message)
                    } else {
                        apiListener.onFailure(t?.localizedMessage.toString())
                    }
                }
            })
        }
}