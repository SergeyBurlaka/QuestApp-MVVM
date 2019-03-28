package com.burlaka.vmpusher.sample.viewmodel

import com.burlaka.vmpusher.BasePushViewModel
import com.burlaka.vmpusher.TaskExecutable
import com.burlaka.vmpusherannotation.BindUiAction
import com.burlaka.vmpusherannotation.BindUiListener
import com.jellyworkz.processor.MainView.showSecureScreenForMainView

//Concept our view model became presenter
//todo add api implement to constructor
class MainViewModel : BasePushViewModel() {

    fun goToSecure() {
        showSecureScreenForMainView() pushBy vmTaskPusher
    }

    //todo call request and show timer than upgrade toolbar on success and change color

    companion object {
        @BindUiListener
        interface MainView : TaskExecutable {
            @BindUiAction(actionId = 1)
            fun showSecureScreen()

            @BindUiAction(actionId = 2)
            fun showProgressBar()

            @BindUiAction(actionId = 3)
            fun upgradeToolbar()

            @BindUiAction(actionId = 4)
            fun changeColor()

            @BindUiAction(actionId = 5)
            fun showClock()
        }
    }
}