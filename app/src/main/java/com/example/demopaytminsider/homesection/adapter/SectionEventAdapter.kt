package com.example.demopaytminsider.homesection.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demopaytminsider.R
import com.example.demopaytminsider.databinding.LayoutSectionEventItemBinding
import com.example.demopaytminsider.helpers.HelperMethods
import com.example.demopaytminsider.helpers.setImageWithGlide
import com.example.demopaytminsider.homesection.dataManager.MasterListEventModel

class SectionEventAdapter(
    private var mEventList: ArrayList<MasterListEventModel>? = null
) : RecyclerView.Adapter<SectionEventAdapter.MyViewHolder>() {
    private lateinit var mContext: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SectionEventAdapter.MyViewHolder {
        mContext = parent.context
        val layoutBinding: LayoutSectionEventItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_section_event_item, parent, false
        )
        return MyViewHolder(layoutBinding)
    }

    override fun getItemCount(): Int = mEventList?.size ?: 0

    override fun onBindViewHolder(holder: SectionEventAdapter.MyViewHolder, position: Int) {
        val eventData = mEventList?.get(position)
        holder.itemBinding.model = eventData
        holder.itemBinding.eventPrice.text = HelperMethods.withPriceOrFree(eventData?.eventPriceDisplayString)
        holder.itemBinding.eventDate.isSelected = true
        eventData?.horizontalCoverImage?.let {
            holder.itemBinding.eventIv.setImageWithGlide(it)
        }
    }

    inner class MyViewHolder(val itemBinding: LayoutSectionEventItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}