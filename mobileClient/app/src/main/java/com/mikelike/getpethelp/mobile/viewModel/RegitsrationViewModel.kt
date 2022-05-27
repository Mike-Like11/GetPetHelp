package com.mikelike.getpethelp.mobile.viewModel

import android.app.PendingIntent.getActivity
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApiImpl

class RegitsrationViewModel: ViewModel() {
    data class RegistrationInput (
        var firstName: String? = null,
        val middleName: String? = null,
        val lastName: String? = null,
        val city: String? = null,
        val phone: String? = null,
        val telegram:Boolean = false,
        val whatsApp:Boolean = false,
        val viber:Boolean = false,
        val age:Int? = 0,
        val email: String? = null,
        val password: String? = null,
        val avatar: Bitmap? = null,
        )
    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var firstName: MutableLiveData<String> = MutableLiveData()
    var middleName: MutableLiveData<String> = MutableLiveData()
    var lastName: MutableLiveData<String> = MutableLiveData()
    var city: MutableLiveData<String> = MutableLiveData()
    var phone: MutableLiveData<String> = MutableLiveData()
    var age: MutableLiveData<Int> = MutableLiveData()
    var avatar: MutableLiveData<Bitmap?> = MutableLiveData()
    private var userMutableLiveData: MutableLiveData<RegistrationInput?> = MutableLiveData()

    fun getUser(): MutableLiveData<RegistrationInput?> {
        return userMutableLiveData
    }

    fun createAccount() {
        val regInput = RegistrationInput(
            firstName = firstName.value,
            middleName.value,
            lastName.value,
            city.value,
            phone.value,
            age = age.value,
            avatar = avatar.value,
            password = password.value,
            email = email.value
        )
        userMutableLiveData.value = regInput
    }
}