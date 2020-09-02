package com.example.demopaytminsider.homeSection.dataManager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demopaytminsider.helpers.Constants
import com.example.demopaytminsider.helpers.UtilMethods
import com.example.demopaytminsider.homeSection.model.HomePageModel
import com.example.demopaytminsider.homeSection.model.ModelEvents
import com.example.demopaytminsider.retrofitManager.ApiResponseCallback
import com.example.demopaytminsider.retrofitManager.ApiServiceProvider
import com.example.demopaytminsider.retrofitManager.UrlContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Response

object EventsRepository : ApiResponseCallback {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    private val parentSuperVisor = SupervisorJob()
    private val groupsLiveDataMap: HashMap<String, MutableLiveData<GroupWiseEventModelList>> by lazy {
        HashMap<String, MutableLiveData<GroupWiseEventModelList>>()
    }
    val masterEventsList: MutableLiveData<ModelEvents> by lazy {
        MutableLiveData<ModelEvents>()
    }
    val homePageLiveData: MutableLiveData<HomePageModel> by lazy {
        MutableLiveData<HomePageModel>()
    }

    fun fetchAllEvents(city: String?) {
        ApiServiceProvider().enqueueApiCall(
            UrlContainer.BASE_URL,
            this,
            UtilMethods.prepareFetchEventsHashMap(city)
        ).homePageData()
    }

    private fun handleFailure(msg: String?) {
        val eventsModel = ModelEvents()
        eventsModel.status = Constants.STATUS_FAILURE
        eventsModel.msg = msg ?: "Some error occurred"
        masterEventsList.value = eventsModel
    }

    private fun handleSuccess(responseModelEvents: ResponseModelEvents) {
        val eventsModel = ModelEvents()

        when (val linkedHashMapOfEvents = responseModelEvents.list?.masterList) {
            null -> {
                eventsModel.status = Constants.STATUS_NO_DATA
                eventsModel.msg = "no data exist"
            }
            else -> {
                if (linkedHashMapOfEvents.size > 0) {
                    eventsModel.status = Constants.STATUS_SUCCESS
                    eventsModel.msg = "success"
                    eventsModel.responseModel = responseModelEvents
                    setupGroupMap(responseModelEvents)

                } else {
                    eventsModel.status = Constants.STATUS_NO_DATA
                    eventsModel.msg = "no data exist"
                }
            }
        }

        masterEventsList.value = eventsModel
    }

    private fun setupGroupMap(responseModelEvents: ResponseModelEvents) {
        setupHomePage(responseModelEvents)
        if (responseModelEvents.groups != null && responseModelEvents.groups.size > 0) {
            responseModelEvents.groups.forEach {
                if (!groupsLiveDataMap.containsKey(it))
                    groupsLiveDataMap[it] = MutableLiveData()
            }
            scope.launch(parentSuperVisor) {
                setupGroupWiseList(responseModelEvents)
            }
        }
    }

    private fun setupHomePage(responseModelEvents: ResponseModelEvents) {
        homePageLiveData.postValue(HomePageModel(responseModelEvents.popularEventList, responseModelEvents.featuredEventList))
    }

    private fun setupGroupWiseList(responseModelEvents: ResponseModelEvents) {
        responseModelEvents.list?.groupList?.let { groupMap ->
            responseModelEvents.groups!!.forEach { groupName ->
                val modelList: ArrayList<MasterListEventModel> = ArrayList()
                if (groupMap.containsKey(groupName)) {
                    val keyList = groupMap[groupName]
                    keyList?.forEach { groupkey ->
                        responseModelEvents.list.masterList?.let {
                            if (it.containsKey(groupkey) && it[groupkey] != null)
                                modelList.add(it[groupkey]!!)
                        }
                    }
                }
                groupsLiveDataMap[groupName]?.postValue(GroupWiseEventModelList(modelList))
            }
        }
    }

    override fun <T> onSuccessCallback(response: Response<T>, serviceCallId: Int) {
        val responseModelEvents = response.body() as? ResponseModelEvents
        if (responseModelEvents == null) {
            handleFailure("body is empty")
        } else {
            handleSuccess(responseModelEvents)
        }
    }

    override fun onFailureCallback(errorMsg: String?, serviceCallId: Int) {
        handleFailure(errorMsg)
    }

    fun getEventsForGroup(groupKey: String): LiveData<GroupWiseEventModelList>? {
        return groupsLiveDataMap[groupKey]
    }
}