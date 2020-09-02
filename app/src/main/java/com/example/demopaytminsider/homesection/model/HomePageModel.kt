package com.example.demopaytminsider.homesection.model

import com.example.demopaytminsider.homesection.dataManager.MasterListEventModel

data class HomePageModel(
    val popularEventList: ArrayList<MasterListEventModel>?,
    val featuredEventList: ArrayList<MasterListEventModel>?
)