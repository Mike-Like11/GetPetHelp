package com.mikelike.getpethelp.mobile.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikelike.getpethelp.mobile.domain.Task
import com.mikelike.getpethelp.mobile.domain.Worker
import com.mikelike.getpethelp.mobile.fakedb.GetPetHelpFakeDB
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApi
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApiImpl
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WorkersViewModel(application: Application) : AndroidViewModel(application) {
    private  var api: GetPetHelpApi
    init {
        api = GetPetHelpApiImpl(application)
    }
    var workers: MutableLiveData<ArrayList<Worker>> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData(true)
    fun getWorkers() = viewModelScope.launch {
        api.getAllWorkers()
        Log.d("zxcxzc",true.toString())
        withContext(viewModelScope.coroutineContext){
            Log.d("zxcxzc",false.toString())
            workers.value = GetPetHelpFakeDB.WORKER_LIST
            if(workers.value!!.size>0){
                loading.value = false
                Log.d("zxcxzc",false.toString())
            }
        }
    }
}