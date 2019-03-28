package com.burlaka.vmpusher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


abstract class BasePushViewModel:
    ViewModel(),
    NavigateVM {

    val vmPusher: NavigateVM.Companion.BaseNavigator = NavigateVM.Companion.BaseNavigator()

    override fun getBaseNavigator() = vmPusher

    infix fun Int.cashTo(navigator: NavigateVM.Companion.BaseNavigator) = navigator.cache(this)
    infix fun Int.pushBy(navigator: NavigateVM.Companion.BaseNavigator) = navigator.navigateTo(this)

    override fun toString(): String {
        return "view model with type ${this::class.java.simpleName}"
    }

    /**
     * Function used on [androidx.lifecycle.Lifecycle.Event.ON_CREATE]
     */
    abstract fun create()

    /**
     * Function used on [androidx.lifecycle.Lifecycle.Event.ON_DESTROY]
     */
    abstract fun detachView()
    companion object {
        data class Message(
            val isFailed: Boolean = true,
            val message: String
        )
    }
}