package com.burlaka.vmpusher.sample

import com.burlaka.vmpusher.INavigate
import com.burlaka.vmpusher.NavigateVM
import com.burlaka.vmpusherannotation.BindUiAction
import com.burlaka.vmpusherannotation.BindUiListener
import com.jellyworkz.processor.Navigator.*


class NavigateLoginViewModel constructor(
    navigator: NavigateVM.Companion.BaseNavigator
) :
    BaseViewModel(navigator) {

    fun join() {
        //  showHomeLoginNavigator().navigate()
    }

    fun login() {
        showHomeLoginNavigator().navigate().cashed()
    }

    fun register() {
        showRegisterLoginNavigator().navigate().cashed()
    }

    fun forgotPathSendEmail() {
        showForgotPathFirstLoginNavigator().navigate().cashed()
    }

    override fun create() {
        navigator!!.restoreFromCash(showPathLoginNavigator())
    }

    fun restorPath() {
        showForgotPathSecondLoginNavigator().navigate().cashed()
    }

    fun onBack() = navigator!!.onBack()

    override fun detachView() {
        //do nothing yet
    }

    companion object {
        @BindUiListener
        interface Navigator : INavigate {
            @BindUiAction(actionId = 2_1_1)
            fun showPathLogin()

            @BindUiAction(actionId = 2_1_02)
            fun showForgotPathFirstLogin()

            @BindUiAction(actionId = 2_1_03)
            fun showForgotPathSecondLogin()

            @BindUiAction(actionId = 2_1_04)
            fun showForgotPathThirdLogin()

            @BindUiAction(actionId = 2_1_05)
            fun showRegisterLogin()

            @BindUiAction(actionId = 2_1_06)
            fun showHomeLogin()
        }
    }
}