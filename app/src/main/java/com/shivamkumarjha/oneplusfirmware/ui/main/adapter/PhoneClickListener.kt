package com.shivamkumarjha.oneplusfirmware.ui.main.adapter

import com.shivamkumarjha.oneplusfirmware.model.InfoData

interface PhoneClickListener {
    fun onCopyClick(infoData: InfoData)
    fun onDownloadClick(infoData: InfoData)
}