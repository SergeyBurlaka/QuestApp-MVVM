package com.burlaka.vmpusher.sample

import androidx.lifecycle.ViewModel
import com.burlaka.vmpusher.NavigateVM
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel(val navigator: NavigateVM.Companion.BaseNavigator? = null) :
    ViewModel(),
    NavigateVM {

    override fun getBaseNavigator() = navigator!!
    fun Int.cashed() = navigator!!.cache(this)
    fun Int.navigate() = navigator!!.navigateTo(this)
    fun Int.doIt() = navigator!!.navigateTo(this)
    fun Int.navigateVia(navigator: NavigateVM.Companion.BaseNavigator) = navigator.navigateTo(this)
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

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

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}