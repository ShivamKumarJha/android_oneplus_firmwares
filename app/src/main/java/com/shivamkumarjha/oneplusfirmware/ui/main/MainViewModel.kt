package com.shivamkumarjha.oneplusfirmware.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivamkumarjha.oneplusfirmware.model.OneplusPhones
import com.shivamkumarjha.oneplusfirmware.model.PhoneInfo
import com.shivamkumarjha.oneplusfirmware.network.ResponseState
import com.shivamkumarjha.oneplusfirmware.repository.PhoneInfoRepository
import com.shivamkumarjha.oneplusfirmware.repository.PhoneModelsRepository
import com.shivamkumarjha.oneplusfirmware.utility.Utility
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _oneplusApiState = MutableLiveData<ResponseState>()
    val oneplusApiState: LiveData<ResponseState> = _oneplusApiState

    private val _oneplusPhones: MutableLiveData<OneplusPhones> = MutableLiveData<OneplusPhones>()
    val oneplusPhones: LiveData<OneplusPhones> = _oneplusPhones

    @Suppress("UNCHECKED_CAST")
    private val phonesApiListener =
        Utility.get()
            .getApiListener(_oneplusApiState, _oneplusPhones as MutableLiveData<Any>, _isLoading)

    private val _phoneInfoApiState = MutableLiveData<ResponseState>()
    val phoneInfoApiState: LiveData<ResponseState> = _phoneInfoApiState

    private val _phoneInfo: MutableLiveData<PhoneInfo> = MutableLiveData<PhoneInfo>()
    val phoneInfo: LiveData<PhoneInfo> = _phoneInfo

    @Suppress("UNCHECKED_CAST")
    private val phonesInfoApiListener =
        Utility.get()
            .getApiListener(_oneplusApiState, _phoneInfo as MutableLiveData<Any>, _isLoading)

    init {
        _isLoading.value = false
    }

    fun getOneplusPhones(storeCode: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            PhoneModelsRepository.getOneplusPhones(storeCode, phonesApiListener)
        }
    }

    fun getPhoneInfo(storeCode: String, phoneCode: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            PhoneInfoRepository.getPhoneInfo(storeCode, phoneCode, phonesInfoApiListener)
        }
    }
}