package com.burlaka.vmpusher

import androidx.lifecycle.ViewModel


abstract class BasePushViewModel :
    ViewModel(),
    VmTaskPusher {

    val vmTaskPusher: BasePusher = BasePusher()
    override fun getBaseNavigator() = vmTaskPusher
    infix fun Int.cashTo(pusher: BasePusher) = pusher.cache(this)
    infix fun Int.pushBy(pusher: BasePusher) = pusher.pushTaskById(this)

    override fun toString(): String {
        return "view model with type ${this::class.java.simpleName}"
    }
}