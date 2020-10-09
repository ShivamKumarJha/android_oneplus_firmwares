package com.shivamkumarjha.oneplusfirmware.utility

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.shivamkumarjha.oneplusfirmware.BuildConfig
import com.shivamkumarjha.oneplusfirmware.R

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
}