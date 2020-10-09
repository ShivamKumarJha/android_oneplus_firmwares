package com.shivamkumarjha.oneplusfirmware.ui.main.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.oneplusfirmware.R
import com.shivamkumarjha.oneplusfirmware.model.InfoData
import com.squareup.picasso.Picasso

class PhoneViewHolder(
    itemView: View,
    private val clickListenerOneplus: PhoneClickListener
) : RecyclerView.ViewHolder(itemView) {
    private val phoneName: TextView = itemView.findViewById(R.id.oneplus_name_text_view)
    private val phoneROMVersion: TextView =
        itemView.findViewById(R.id.oneplus_rom_version_text_view)
    private val phoneROMDate: TextView = itemView.findViewById(R.id.oneplus_rom_date_text_view)
    private val phoneROMSize: TextView = itemView.findViewById(R.id.oneplus_rom_size_text_view)
    private val phoneROMMD5: TextView = itemView.findViewById(R.id.oneplus_rom_md5_text_view)
    private val phoneROMLink: Button = itemView.findViewById(R.id.oneplus_rom_link_text_view)
    private val phoneImage: ImageView = itemView.findViewById(R.id.oneplus_image_view_id)
    private val copyImageButton: ImageButton = itemView.findViewById(R.id.oneplus_copy_link_id)
    private lateinit var phoneInfo: InfoData

    init {
        copyImageButton.setOnClickListener {
            clickListenerOneplus.onCopyClick(phoneInfo)
        }
        phoneROMLink.setOnClickListener {
            clickListenerOneplus.onDownloadClick(phoneInfo)
        }
    }

    @SuppressLint("SetTextI18n")
    fun initialize(phoneInfo: InfoData) {
        this.phoneInfo = phoneInfo
        phoneName.text = phoneInfo.phoneName
        phoneROMVersion.text = "Version" + phoneInfo.versionNo
        phoneROMDate.text = "Date " + phoneInfo.versionReleaseTime
        phoneROMSize.text = "Size " + phoneInfo.versionSize
        phoneROMMD5.text = "MD5 " + phoneInfo.versionSign
        // Load book image from URL
        Picasso.get().load(phoneInfo.phoneImage).fit().centerInside()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(phoneImage)
    }
}
