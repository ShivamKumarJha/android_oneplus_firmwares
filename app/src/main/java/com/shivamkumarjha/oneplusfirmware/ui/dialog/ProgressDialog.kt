package com.shivamkumarjha.oneplusfirmware.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import com.github.ybq.android.spinkit.SpinKitView
import com.shivamkumarjha.oneplusfirmware.R

class ProgressDialog(activity: Activity) : Dialog(activity),
    View.OnClickListener {
    private lateinit var spinKitView: SpinKitView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_progress)
        spinKitView = findViewById(R.id.dialog_progress)
    }

    override fun onClick(v: View) {
        dismiss()
    }
}