package com.shivamkumarjha.oneplusfirmware.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivamkumarjha.oneplusfirmware.model.OneplusPhones
import com.shivamkumarjha.oneplusfirmware.network.ApiListener
import com.shivamkumarjha.oneplusfirmware.network.ResponseState
import com.shivamkumarjha.oneplusfirmware.repository.PhoneModelsRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _phonesApiState = MutableLiveData<ResponseState>()
    val phonesApiState: LiveData<ResponseState> = _phonesApiState

    private val _oneplusPhones: MutableLiveData<OneplusPhones> = MutableLiveData<OneplusPhones>()
    val oneplusPhones: LiveData<OneplusPhones> = _oneplusPhones

    private val updateApiListener = object : ApiListener {
        override fun onResponse(t: Any?) {
            _phonesApiState.value = ResponseState(isSuccess = true)
            _oneplusPhones.value = t as OneplusPhones
            _isLoading.value = false
        }

        override fun onResponseError(t: Any?) {
            _phonesApiState.value = ResponseState(responseCode = t, isSuccess = false)
            _oneplusPhones.value = null
            _isLoading.value = false
        }

        override fun onFailure(t: Any?) {
            _phonesApiState.value = ResponseState(errorMessage = t, isSuccess = false)
            _oneplusPhones.value = null
            _isLoading.value = false
        }

        override fun onOffline(t: Any?) {
            _phonesApiState.value = ResponseState(isOffline = t, isSuccess = false)
            _oneplusPhones.value = null
            _isLoading.value = false
        }
    }

    init {
        _isLoading.value = false
    }

    fun getOneplusPhones(storeCode: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            PhoneModelsRepository.getOneplusPhones(storeCode, updateApiListener)
        }
    }
}