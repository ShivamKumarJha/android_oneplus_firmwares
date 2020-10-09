package com.shivamkumarjha.oneplusfirmware.ui.main

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.shivamkumarjha.oneplusfirmware.R
import com.shivamkumarjha.oneplusfirmware.config.Constants
import com.shivamkumarjha.oneplusfirmware.model.InfoData
import com.shivamkumarjha.oneplusfirmware.ui.dialog.ProgressDialog
import com.shivamkumarjha.oneplusfirmware.ui.main.adapter.PhoneAdapter
import com.shivamkumarjha.oneplusfirmware.ui.main.adapter.PhoneClickListener
import com.shivamkumarjha.oneplusfirmware.utility.Utility

class MainActivity : AppCompatActivity() {

    private lateinit var rootLayout: LinearLayout
    private lateinit var mainViewModel: MainViewModel
    private lateinit var progressDialog: ProgressDialog
    private lateinit var recyclerView: RecyclerView
    private lateinit var phoneAdapter: PhoneAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViews()
        observeData()
    }

    private fun setViews() {
        rootLayout = findViewById(R.id.root_layout)
        progressDialog = ProgressDialog(this)
        progressDialog.setCanceledOnTouchOutside(false)
        // recycler view
        recyclerView = findViewById(R.id.oneplus_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        phoneAdapter = PhoneAdapter(object : PhoneClickListener {
            override fun onCopyClick(infoData: InfoData) {
                Utility.get().copyTextToClipboard(this@MainActivity, infoData.versionLink)
            }

            override fun onDownloadClick(infoData: InfoData) {
                Utility.get().openLinkInBrowser(this@MainActivity, infoData.versionLink)
            }

        })
        recyclerView.adapter = phoneAdapter
    }

    private fun observeData() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getOneplusPhones(Constants.STORE_CODE)
        mainViewModel.phonesApiState.observe(this, {
            when {
                it.isOffline != null -> {
                    Utility.get().getSnackBar(
                        rootLayout,
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
                if (it.errCode != 1 && it.data != null) {
                    for (phone in it.data) {
                        mainViewModel.getPhoneInfo(Constants.STORE_CODE, phone.phoneCode)
                    }
                } else {
                    Utility.get().getSnackBar(
                        rootLayout,
                        "Failed! " + it.errMsg,
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(Color.RED).show()
                }
            }
        })
        mainViewModel.phonesInfoApiState.observe(this, {
            when {
                it.isOffline != null -> {
                    Utility.get().getSnackBar(
                        rootLayout,
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
                if (it.errCode != 1 && it.data != null) {
                    phoneAdapter.addInfoData(it.data[0])
                } else {
                    Utility.get().getSnackBar(
                        rootLayout,
                        "Failed! " + it.errMsg,
                        Snackbar.LENGTH_LONG
                    ).setBackgroundTint(Color.RED).show()
                }
            }
        })
    }
}