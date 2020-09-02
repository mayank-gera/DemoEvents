package com.example.demopaytminsider.homeSection.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demopaytminsider.R
import com.example.demopaytminsider.databinding.LayoutProductSectionBinding
import com.example.demopaytminsider.homeSection.model.HomePageModel

class AllEventsAdapter(private var mHomePageModel: HomePageModel? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    init {
        setupItemList()
    }

    private lateinit var mContext: Context
    private var itemList: List<Int> = ArrayList()
    private val viewTypeFeatureSection = 1
    private val viewTypePopularSection = 2


    fun update(homePageModel: HomePageModel?) {
        mHomePageModel = homePageModel
        setupItemList()
        notifyDataSetChanged()
    }

    private fun setupItemList() {
        val tempItemList: ArrayList<Int> = ArrayList()
        if (mHomePageModel?.featuredEventList?.isEmpty() == false)
            tempItemList.add(viewTypeFeatureSection)
        if (mHomePageModel?.popularEventList?.isEmpty() == false)
            tempItemList.add(viewTypePopularSection)
        itemList = tempItemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_product_section, parent, false
        )
        return if (viewType == viewTypePopularSection)
            FeaturedViewHolder(binding as LayoutProductSectionBinding)
        else
            PopularViewHolder(binding as LayoutProductSectionBinding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return itemList[position]
    }

    inner class FeaturedViewHolder(itemBinding: LayoutProductSectionBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.sectionHeading.text = "FEATURED EVENTS"
            val adapter = SectionEventAdapter(mHomePageModel?.featuredEventList)
//            itemBinding.sectionRV.attachSmoothScrollListener()
            itemBinding.sectionRV.layoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            itemBinding.sectionRV.adapter = adapter
        }
    }

    inner class PopularViewHolder(itemBinding: LayoutProductSectionBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.sectionHeading.text = "POPULAR EVENTS"
            val adapter = SectionEventAdapter(mHomePageModel?.popularEventList)
//            itemBinding.sectionRV.attachSmoothScrollListener()
            itemBinding.sectionRV.layoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            itemBinding.sectionRV.adapter = adapter
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }
}