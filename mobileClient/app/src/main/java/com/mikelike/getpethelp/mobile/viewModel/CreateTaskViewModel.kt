package com.mikelike.getpethelp.mobile.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApi
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApiImpl

class CreateTaskViewModel(application: Application) : AndroidViewModel(application) {
    private  var api: GetPetHelpApi
    data class TaskInput (
        val name: String? = null,
        val description: String? = null,
        val dateOfPerformance: String? = null,
        val latitude: Double? = null,
        val longitude: Double? = null
    )
    init {
        api = GetPetHelpApiImpl(application)
    }
    var name: MutableLiveData<String> = MutableLiveData()
    var description: MutableLiveData<String> = MutableLiveData()
    var dateOfPerformance: MutableLiveData<String> = MutableLiveData()
    var latitude: MutableLiveData<Double> = MutableLiveData()
    var longitude: MutableLiveData<Double> = MutableLiveData()
    fun createTask() {
        val taskInput = TaskInput(
            name = name.value,
            description = description.value,
            dateOfPerformance = dateOfPerformance.value,
            latitude = latitude.value,
            longitude = longitude.value
        )
        api.createTask(taskInput)
    }
}