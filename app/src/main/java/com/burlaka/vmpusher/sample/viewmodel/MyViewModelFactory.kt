package com.burlaka.vmpusher.sample.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.burlaka.vmpusher.NavigateVM


/**
 * @author BRCJU
 * @since 28.03.2019
 */
class MyViewModelFactory(application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass == MainViewModel::class.java) {
            MainViewModel() as T
        } else throw RuntimeException()
    }
}