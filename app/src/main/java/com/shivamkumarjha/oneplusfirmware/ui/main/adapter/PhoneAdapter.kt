package com.shivamkumarjha.oneplusfirmware.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.shivamkumarjha.oneplusfirmware.R
import com.shivamkumarjha.oneplusfirmware.model.InfoData
import java.util.*
import kotlin.collections.ArrayList

class PhoneAdapter(private val phoneClickListener: PhoneClickListener) :
    RecyclerView.Adapter<PhoneViewHolder>(), Filterable {
    private var phones: ArrayList<InfoData> = arrayListOf()
    private var backupList: ArrayList<InfoData> = phones

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_content_phones, parent, false)
        return PhoneViewHolder(itemView, phoneClickListener)
    }

    override fun getItemCount(): Int = phones.size

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        holder.initialize(phones[position])
    }

    fun addInfoData(phone: InfoData) {
        this.phones.add(phone)
        backupList = this.phones
        notifyItemInserted(this.phones.size)
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                var filteredList: ArrayList<InfoData>? = ArrayList()
                if (charSequence.toString().isEmpty()) {
                    filteredList = backupList
                } else {
                    for (oneplusPhone in phones) {
                        if (oneplusPhone.phoneName.toLowerCase(Locale.ROOT)
                                .contains(charSequence.toString().toLowerCase(Locale.ROOT)) ||
                            oneplusPhone.versionNo.toLowerCase(Locale.ROOT)
                                .contains(charSequence.toString().toLowerCase(Locale.ROOT)) ||
                            oneplusPhone.phoneImage.toLowerCase(Locale.ROOT)
                                .contains(charSequence.toString().toLowerCase(Locale.ROOT)) ||
                            oneplusPhone.versionReleaseTime.toLowerCase(Locale.ROOT)
                                .contains(charSequence.toString().toLowerCase(Locale.ROOT)) ||
                            oneplusPhone.versionSize.toLowerCase(Locale.ROOT)
                                .contains(charSequence.toString().toLowerCase(Locale.ROOT))
                        ) {
                            filteredList?.add(oneplusPhone)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults?
            ) {
                phones = filterResults?.values as ArrayList<InfoData>
                notifyDataSetChanged()
            }
        }
    }
}
