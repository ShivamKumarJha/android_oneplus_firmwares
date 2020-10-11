package com.shivamkumarjha.oneplusfirmware.utility

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.shivamkumarjha.oneplusfirmware.BuildConfig
import com.shivamkumarjha.oneplusfirmware.R
import com.shivamkumarjha.oneplusfirmware.network.ApiListener
import com.shivamkumarjha.oneplusfirmware.network.ResponseState

class Utility {
    private val tag = "Utility"

    companion object {
        private var INSTANCE: Utility? = null

        fun get(): Utility {
            if (INSTANCE == null) {
                INSTANCE = Utility()
            }
            return INSTANCE!!
        }
    }

    fun getSnackBar(view: View, message: String, length: Int): Snackbar {
        return Snackbar.make(view, message, length)
    }

    fun openLinkInBrowser(context: Context, url: String) {
        if (URLUtil.isValidUrl(url)) {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(url)
            context.startActivity(openURL)
        }
    }

    fun debugToast(context: Context, message: String) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            Log.d(tag, message)
        }
    }

    fun copyTextToClipboard(context: Context, textToCopy: String) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("ROM_LINK", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, context.resources.getString(R.string.copied), Toast.LENGTH_SHORT)
            .show()
    }

    fun apiState(context: Context, view: View, it: ResponseState) {
        when {
            it.isOffline != null -> {
                getSnackBar(
                    view,
                    "Failed! " + it.isOffline,
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(Color.RED).show()
            }
            it.errorMessage != null -> {
                debugToast(context, "Failed! " + it.errorMessage)
            }
            it.responseCode != null -> {
                debugToast(context, "Failed! " + it.responseCode)
            }
        }
    }

    fun getApiListener(
        state: MutableLiveData<ResponseState>,
        data: MutableLiveData<Any>,
        loading: MutableLiveData<Boolean>
    ): ApiListener {
        return object : ApiListener {
            override fun onResponse(t: Any?) {
                state.value = ResponseState(isSuccess = true)
                data.value = t
                loading.value = false
            }

            override fun onResponseError(t: Any?) {
                state.value = ResponseState(responseCode = t, isSuccess = false)
                data.value = null
                loading.value = false
            }

            override fun onFailure(t: Any?) {
                state.value = ResponseState(errorMessage = t, isSuccess = false)
                data.value = null
                loading.value = false
            }

            override fun onOffline(t: Any?) {
                state.value = ResponseState(isOffline = t, isSuccess = false)
                data.value = null
                loading.value = false
            }
        }
    }
}