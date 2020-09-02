package com.example.demopaytminsider.homeSection.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demopaytminsider.R
import com.example.demopaytminsider.databinding.LayoutSectionEventItemBinding
import com.example.demopaytminsider.homeSection.dataManager.MasterListEventModel

class SectionEventAdapter(
    private var mEventList: ArrayList<MasterListEventModel>? = null
) : RecyclerView.Adapter<SectionEventAdapter.MyViewHolder>() {

    private lateinit var mContext: Context

    fun updateList(eventList: ArrayList<MasterListEventModel>?) {
        mEventList = eventList
        notifyDataSetChanged()
    }

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
        holder.itemBinding.eventPrice.text = if (eventData?.eventPriceDisplayString?.equals(
                "0",
                ignoreCase = false
            ) == true
        ) "FREE" else "₹${eventData?.eventPriceDisplayString}"
        holder.itemBinding.eventDate.isSelected = true
        setImageWithGlide(holder.itemBinding.eventIv, eventData)
    }

    private fun setImageWithGlide(eventIv: ImageView, eventData: MasterListEventModel?) {

        eventData?.horizontalCoverImage?.let {
            Glide
                .with(mContext)
                .load(it)
                .placeholder(R.drawable.bg_default_event)
                .into(eventIv)
        }
    }

    inner class MyViewHolder(val itemBinding: LayoutSectionEventItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}