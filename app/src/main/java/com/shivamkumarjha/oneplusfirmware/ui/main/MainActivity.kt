package com.shivamkumarjha.oneplusfirmware.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
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
import com.shivamkumarjha.oneplusfirmware.utility.hideKeyboard

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val search: MenuItem = menu.findItem(R.id.search_id)
        val searchView: SearchView = search.actionView as SearchView
        val searchIcon = searchView.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)
        val cancelIcon = searchView.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.WHITE)
        val searchTextView = searchView.findViewById<TextView>(R.id.search_src_text)
        searchTextView.setTextColor(Color.WHITE)
        searchTextView.hint = resources.getString(R.string.search_phone)
        searchTextView.setHintTextColor(ContextCompat.getColor(this, R.color.white))
        searchView.queryHint = resources.getString(R.string.search_phone)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                return false
            }

            override fun onQueryTextChange(filter: String?): Boolean {
                phoneAdapter.filter.filter(filter)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}