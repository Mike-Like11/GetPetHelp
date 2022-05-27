package com.mikelike.getpethelp.mobile.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mikelike.getpethelp.mobile.MainActivity
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApi
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApiImpl
import kotlinx.coroutines.launch
import org.json.JSONObject


class LoginViewModel(application: Application):AndroidViewModel(application) {
    data class LoginInput (
        val email: String? = null,
        val password: String? = null,
    )
    val sharedpreferences = application.applicationContext.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var errors: MutableLiveData<Boolean>  = MutableLiveData(false)
    var loading: MutableLiveData<Boolean>  = MutableLiveData(false)
    fun login()= viewModelScope.launch {
        loading.value =true
        val loginInput = LoginInput(
            password = password.value,
            email = email.value
        )
        with(viewModelScope.coroutineContext) {
            val queue = Volley.newRequestQueue(getApplication())
            val url = "http://192.168.1.4:8080/api/auth/login"
            val params = HashMap<String, String>()
            val json = JSONObject()
            json.put("email", loginInput.email.toString())
            json.put("password", loginInput.password.toString())
            val request = object : StringRequest(
                Request.Method.POST, url,
                { stringResponse ->
                    loading.value = false
                    errors.value = false
                    val editor: SharedPreferences.Editor = sharedpreferences.edit()
                    editor.putString("key", stringResponse.toString())
                    editor.commit() // or editor.apply();
                },
                { volleyError -> errors.value = true }) {
                override fun getBody(): ByteArray {
                    return json.toString().toByteArray();
                }

                override fun getBodyContentType(): String {
                    return "application/json";
                }
            }
            queue.add(request)
        }
    }

}