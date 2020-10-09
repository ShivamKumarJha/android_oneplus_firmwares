package com.shivamkumarjha.oneplusfirmware.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.shivamkumarjha.oneplusfirmware.R
import com.shivamkumarjha.oneplusfirmware.config.Constants
import com.shivamkumarjha.oneplusfirmware.ui.dialog.ProgressDialog
import com.shivamkumarjha.oneplusfirmware.utility.Utility

class MainActivity : AppCompatActivity() {

    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var mainViewModel: MainViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // views
        constraintLayout = findViewById(R.id.constraint_layout)
        progressDialog = ProgressDialog(this)
        progressDialog.setCanceledOnTouchOutside(false)
        // view model
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getOneplusPhones(Constants.STORE_CODE)
        mainViewModel.phonesApiState.observe(this, {
            when {
                it.isOffline != null -> {
                    Utility.get().getSnackBar(
                        constraintLayout,
                        "Failed! " + it.isOffline,
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(Color.RED).show()
                }
                it.errorMessage != null -> {
                    Utility.get().debugToast(this, "Failed! " + it.errorMessage)
                }
                it.responseCode != null -> {
                    Utility.get().debugToast(this, "Failed! " + it.responseCode)
                }
            }
        })
        mainViewModel.isLoading.observe(this, {
            if (it)
                progressDialog.show()
            else
                progressDialog.dismiss()
        })
        mainViewModel.oneplusPhones.observe(this, {
            if (it != null) {
                for (phone in it.data) {
                    mainViewModel.getPhoneInfo(Constants.STORE_CODE, phone.phoneCode)
                    Utility.get().debugToast(this, phone.phoneCode)
                }
            }
        })
        mainViewModel.phonesInfoApiState.observe(this, {
            when {
                it.isOffline != null -> {
                    Utility.get().getSnackBar(
                        constraintLayout,
                        "Failed! " + it.isOffline,
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(Color.RED).show()
                }
                it.errorMessage != null -> {
                    Utility.get().debugToast(this, "Failed! " + it.errorMessage)
                }
                it.responseCode != null -> {
                    Utility.get().debugToast(this, "Failed! " + it.responseCode)
                }
            }
        })
        mainViewModel.phoneInfo.observe(this, {
            if (it != null) {
                Utility.get().debugToast(this, it.toString())
            }
        })
    }
}