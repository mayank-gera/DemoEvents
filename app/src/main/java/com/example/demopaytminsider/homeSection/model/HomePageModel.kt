package com.example.demopaytminsider.homeSection.model

import com.example.demopaytminsider.homeSection.dataManager.MasterListEventModel

data class HomePageModel(
    val popularEventList: ArrayList<MasterListEventModel>?,
    val featuredEventList: ArrayList<MasterListEventModel>?
)