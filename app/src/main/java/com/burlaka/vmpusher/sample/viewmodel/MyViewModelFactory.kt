package com.burlaka.vmpusher.sample.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * @author BRCJU
 * @since 28.03.2019
 */
class MyViewModelFactory(application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass == MainPresenterViewModel::class.java) {
            MainPresenterViewModel(TimerEngine()) as T
        } else throw RuntimeException()
    }
}