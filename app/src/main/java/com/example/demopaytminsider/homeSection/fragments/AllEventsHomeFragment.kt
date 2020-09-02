package com.example.demopaytminsider.homeSection.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demopaytminsider.databinding.FragmentAllEventsHomeBinding
import com.example.demopaytminsider.helpers.Constants
import com.example.demopaytminsider.homeSection.adapter.AllEventsAdapter
import com.example.demopaytminsider.homeSection.dataManager.MainViewModel
import com.example.demopaytminsider.homeSection.model.HomePageModel

class AllEventsHomeFragment : Fragment() {
    private lateinit var mAdapter: AllEventsAdapter
    private var homePageModel: HomePageModel? = null
    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentAllEventsHomeBinding
    private var mEventMainViewModel: MainViewModel? = null
    private var groupTitle: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            groupTitle = it.getString(Constants.GROUP_TITLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAllEventsHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        if (activity != null) {
            mEventMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            if (mEventMainViewModel?.getModelForHome()?.hasActiveObservers() == false)
                mEventMainViewModel?.getModelForHome()
                    ?.observe(viewLifecycleOwner, Observer {
                        homePageModel = it
                        setupEventData()
                    })
        }
    }

    private fun initViews(){
        mAdapter = AllEventsAdapter(homePageModel)
        mBinding.rvEvents.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mBinding.rvEvents.adapter = mAdapter
    }

    private fun setupEventData() {
        mAdapter.update(homePageModel)
    }


    companion object {
        @JvmStatic
        fun newInstance(groupTitle: String) =
            AllEventsHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.GROUP_TITLE, groupTitle)
                }
            }
    }
}