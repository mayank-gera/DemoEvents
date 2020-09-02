package com.example.demopaytminsider.location

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demopaytminsider.R
import com.example.demopaytminsider.databinding.BottomSheetOptionsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LocationBottomSheet : BottomSheetDialogFragment(), LocationAdapter.OnItemClickedListener {
    lateinit var mContext: Context
    lateinit var mBinding: BottomSheetOptionsBinding
    private var mListener: CitySelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = BottomSheetOptionsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        mBinding.locationRV.adapter =
            LocationAdapter(mContext.resources.getStringArray(R.array.Cities).toList(), this)

        mBinding.locationRV.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        if (context is CitySelectedListener) {
            mListener = context as CitySelectedListener
        } else {
            throw RuntimeException(
                context.toString()
                    .toString() + " must implement CitySelectedListener"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface CitySelectedListener {
        fun onCitySelected(item: String)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): LocationBottomSheet {
            val fragment =
                LocationBottomSheet()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onItemClicked(value: String) {
        dismissAllowingStateLoss()
        mListener?.onCitySelected(value)
    }

}