package com.example.demopaytminsider.homesection.dataManager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.demopaytminsider.helpers.Constants
import com.example.demopaytminsider.homesection.model.HomePageModel
import com.example.demopaytminsider.homesection.model.ModelEvents

class MainViewModel : ViewModel() {
    private val eventsRepository by lazy {
        EventsRepository()
    }
    fun fetchAllEvents(city: String? = Constants.DEFAULT_CITY) {
        eventsRepository.fetchAllEvents(city)
    }

    fun getAllEvents(): LiveData<ModelEvents>? {
        return eventsRepository.masterEventsList
    }

    fun getEventsForGroup(groupKey: String): LiveData<GroupWiseEventModelList>? {
        return eventsRepository.getEventsForGroup(groupKey)
    }

    fun getModelForHome(): LiveData<HomePageModel>? {
        return eventsRepository.homePageLiveData
    }

    override fun onCleared() {
        super.onCleared()
        eventsRepository.stopProcesses()
    }
}