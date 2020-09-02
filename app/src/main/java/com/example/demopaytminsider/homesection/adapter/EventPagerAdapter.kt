package com.example.demopaytminsider.homesection.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demopaytminsider.helpers.Constants
import com.example.demopaytminsider.homesection.fragments.AllEventsHomeFragment
import com.example.demopaytminsider.homesection.fragments.EventListingFragment


class EventPagerAdapter(fa: FragmentActivity, private val groups: ArrayList<String>) :
    FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment {
        if (Constants.ALL_EVENTS == groups[position])
            return AllEventsHomeFragment.newInstance(groups[position])
        return EventListingFragment.newInstance(groups[position])
    }

    override fun getItemCount(): Int {
        return groups.size
    }
}