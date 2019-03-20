package com.burlaka.vmpusher

import android.util.Log


val testNavigator: NavigateVM.Companion.BaseNavigator
    get() = NavigateVM.Companion.BaseNavigator()

const val screen_1 = 1
const val screen_2 = 2
const val screen_3 = 3

fun NavigateVM.Companion.BaseNavigator.testNavigateOneScreen() {
    "testNavigateOneScreen".divide()

    testNavigator.apply {

        navigateTo(screen_1)
        cache(screen_1)

        (vector.value!!.peekContent() == screen_1) assert "Navigate to screen 1"
        (getCash() == null) assert "no screens on stack"

        onBack().not() assert "back return false"
    }
}

fun NavigateVM.Companion.BaseNavigator.testNavigateCashTwoScreens() {
    "testNavigateCashTwoScreens".divide()

    testNavigator.apply {

        navigateTo(screen_1)
        cache(screen_1)

        navigateTo(screen_2)
        cache(screen_2)

        (vector.value!!.peekContent() == screen_2) assert "Current navigate is screen 2"
        (getCash()!!.peekContent() == screen_1) assert "screen 1 available from cash"

    }
}

fun NavigateVM.Companion.BaseNavigator.tesNavigateCashOnBackTwoScreens() {
    "tesNavigateCashOnBackTwoScreens".divide()

    testNavigator.apply {

        navigateTo(screen_1)
        cache(screen_1)

        navigateTo(screen_2)
        cache(screen_2)

        onBack() assert "back return success"

        (vector.value!!.peekContent() == screen_1) assert "App navigate to previous 1 screen"

        (getCash() == null) assert "no screens on stack"

    }
}

fun NavigateVM.Companion.BaseNavigator.tesNavigateCashAfterRestoreFromCash() {
    "tesNavigateCashAfterRestoreFromCash".divide()

    testNavigator.apply {

        navigateTo(screen_1)
        cache(screen_1)

        vector.value!!.getContentIfNotHandled()

        restoreFromCash(screen_2)

        navigateTo(screen_2)
        cache(screen_2)

        onBack() assert "back return success"

        (vector.value!!.peekContent() == screen_1) assert "App navigate to previous 1 screen"

        (getCash() == null) assert "no screens on stack"

    }
}

fun NavigateVM.Companion.BaseNavigator.tesNavigateCashAfterRestoreFromCash_2() {
    "tesNavigateCashAfterRestoreFromCash_2".divide()

    testNavigator.apply {

        restoreFromCash(screen_2)

        navigateTo(screen_3)
        cache(screen_3)

        onBack() assert "back return success"

        (vector.value!!.peekContent() == screen_2) assert "App navigate to previous 2 screen"

        (getCash() == null) assert "no screens on stack"

    }
}

infix fun Boolean.assert(mesg: String) {
    Log.d(LOG_TEST, "Success: $mesg value is $this  time is ${System.currentTimeMillis()}")
    if (this.not()) {
        Log.e(LOG_TEST, "Assert failed on try to check $mesg")
        throw RuntimeException("Assert failed on try to check $mesg")
    }
}

fun String.divide() {
    Log.d(LOG_TEST, "= = = = = = = = = = = =  $this  = = = = = = = = = = = = = = = = = =")
}

const val LOG_TEST = "TEST_NAVIGATOR"