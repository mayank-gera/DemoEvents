package com.example.demopaytminsider.homeSection.dataManager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.demopaytminsider.helpers.Constants
import com.example.demopaytminsider.homeSection.model.HomePageModel
import com.example.demopaytminsider.homeSection.model.ModelEvents

class MainViewModel : ViewModel() {

    fun fetchAllEvents(city: String? = Constants.DEFAULT_CITY) {
        EventsRepository.fetchAllEvents(city)
    }

    fun getAllEvents(): LiveData<ModelEvents>? {
        return EventsRepository.masterEventsList
    }

    fun getEventsForGroup(groupKey: String): LiveData<GroupWiseEventModelList>? {
        return EventsRepository.getEventsForGroup(groupKey)
    }

    fun getModelForHome(): LiveData<HomePageModel>? {
        return EventsRepository.homePageLiveData
    }
}