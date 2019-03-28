package com.burlaka.vmpusher.sample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


open class LoginViewModel : ViewModel() {

    var email: String = ""
    val error = MutableLiveData<String>()
    val authResponse = MutableLiveData<Boolean>()
    val checkEmailResponse = MutableLiveData<Boolean>()


}