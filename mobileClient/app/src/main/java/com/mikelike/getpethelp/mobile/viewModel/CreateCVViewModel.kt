package com.mikelike.getpethelp.mobile.viewModel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApi
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApiImpl
import kotlinx.coroutines.launch

class CreateCVViewModel(application: Application) : AndroidViewModel(application) {
    private var api: GetPetHelpApi

    data class WorkerInfoInput(
        val experience: String? = null,
        val preferences: String? = null,
    )

    init {
        api = GetPetHelpApiImpl(application)
    }

    var experience: MutableLiveData<String> = MutableLiveData()
    var preferences: MutableLiveData<String> = MutableLiveData()
    fun createCV()=  viewModelScope.launch {
        with(viewModelScope.coroutineContext) {
            val workerInfoInput = WorkerInfoInput(
                experience = experience.value,
                preferences = preferences.value
            )
            api.createCV(workerInfoInput)
        }
    }
}