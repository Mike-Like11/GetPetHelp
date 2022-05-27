package com.mikelike.getpethelp.mobile.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.mikelike.getpethelp.mobile.MainActivity
import com.mikelike.getpethelp.mobile.domain.Task
import com.mikelike.getpethelp.mobile.domain.User
import com.mikelike.getpethelp.mobile.domain.Worker
import com.mikelike.getpethelp.mobile.fakedb.GetPetHelpFakeDB
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApi
import kotlinx.coroutines.launch


class MyProfileViewModelFactory(
    private val getPetHelpApi: GetPetHelpApi,
    private val application: Application
): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return MyProfileViewModel(getPetHelpApi, application) as T
    }
}
class MyProfileViewModel(getPetHelpApi: GetPetHelpApi, application: Application) : AndroidViewModel(
    application
) {
    private var api: GetPetHelpApi
    init {
        api = getPetHelpApi
    }
    var user: MutableLiveData<User> = MutableLiveData()
    var worker: MutableLiveData<Worker> = MutableLiveData()
    var myTasks: MutableLiveData<ArrayList<Task>> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData(true)
    fun getUserInfo() =  viewModelScope.launch {
        api.getCurrentUserInfo()
        api.getCurrentUserWorker()
        api.getCurrentUserTasks()
       with(viewModelScope.coroutineContext) {
            val gson = Gson()
            val sharedpreferences = getApplication<Application>().applicationContext.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
            var json: String = sharedpreferences.getString("user", "").toString()
            if(json!="") {
                Log.d("qewq2",json)
                val obj: User = gson.fromJson(json, User::class.java)
                Log.d("qewq2",obj.toString())
                user.postValue(obj)
                loading.postValue(false)
            }
           json = sharedpreferences.getString("worker", "").toString()
           if(json!="") {
               Log.d("qewq2",json)
               val obj: Worker  = gson.fromJson(json, Worker::class.java)
               Log.d("qewq2",obj.toString())
               worker.postValue(obj)
           }
           Log.d("dsdsd",GetPetHelpFakeDB.MY_TASK_LIST.toString())
           myTasks.value = GetPetHelpFakeDB.MY_TASK_LIST
           if(GetPetHelpFakeDB.TASK_LIST.size>0){
               loading.value = false
           }
        }
    }
}