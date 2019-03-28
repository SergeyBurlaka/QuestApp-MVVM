package com.burlaka.vmpusher.sample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.burlaka.vmpusher.BasePushViewModel
import com.burlaka.vmpusher.Event
import com.burlaka.vmpusher.INavigate
import com.burlaka.vmpusherannotation.BindUiAction
import com.burlaka.vmpusherannotation.BindUiListener
import com.jellyworkz.processor.Navigator.showSecureScreenNavigator


class MainViewModel : BasePushViewModel() {

    fun goToSecure() {
        showSecureScreenNavigator() pushBy vmPusher
    }

    override fun create() {
    }

    override fun detachView() {
        //do nothing yet
    }

    val messages: LiveData<Event<BasePushViewModel.Companion.Message>>
        get() = _messages
    protected var _messages = MutableLiveData<Event<BasePushViewModel.Companion.Message>>()

    protected infix fun MutableLiveData<Event<BasePushViewModel.Companion.Message>>.showMessage(mess: String) {
        this.value = Event(BasePushViewModel.Companion.Message(isFailed = false, message = mess))
    }

    protected infix fun MutableLiveData<Event<BasePushViewModel.Companion.Message>>.showErrorMessage(mess: String) {
        this.value = Event(BasePushViewModel.Companion.Message(isFailed = true, message = mess))
    }

    companion object {
        @BindUiListener
        interface Navigator : INavigate {
            @BindUiAction(actionId = 1)
            fun showSecureScreen()
        }
    }
}