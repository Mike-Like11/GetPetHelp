package com.mikelike.getpethelp.mobile.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikelike.getpethelp.mobile.domain.Task
import com.mikelike.getpethelp.mobile.fakedb.GetPetHelpFakeDB
import com.mikelike.getpethelp.mobile.fakedb.GetPetHelpFakeDB.Companion.TASK_LIST
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApi
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApiImpl
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksViewModel(application: Application) : AndroidViewModel(application) {
        private  var api: GetPetHelpApi
        init {
            api = GetPetHelpApiImpl(application)
        }
        var tasks: MutableLiveData<ArrayList<Task>> = MutableLiveData()
        var loading:MutableLiveData<Boolean> = MutableLiveData(true)
        fun getTasks() = viewModelScope.launch {
            api.getAllTasks()
            withContext(viewModelScope.coroutineContext){
                tasks.value = TASK_LIST
                if(TASK_LIST.size>0){
                    loading.value = false
                }
            }
        }
}