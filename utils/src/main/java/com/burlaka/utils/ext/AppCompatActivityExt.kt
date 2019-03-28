package com.burlaka.utils.ext

import android.util.Log
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


inline fun FragmentActivity.setContentFragment(
    enter: Int,
    exit: Int,
    containerViewId: Int,
    backStack: Boolean = false,
    f: () -> Fragment
): Fragment {
    val manager = supportFragmentManager
    return f().apply {
        manager.beginTransaction().setCustomAnimations(enter, exit, enter, exit).replace(containerViewId, this)
            .addToBackStack("Fr")
            .commit()
    }
}

inline fun FragmentActivity.setContentFragment(
    containerViewId: Int,
    backStack: Boolean = false,
    f: () -> Fragment

): Fragment? {
    val manager = supportFragmentManager
    return f().apply {
        if (backStack) {
            manager.beginTransaction().replace(containerViewId, this, "Fr").addToBackStack("Fr").commit()
        } else {
            manager.beginTransaction().replace(containerViewId, this, "Fr").commit()
        }
    }
}

fun FragmentActivity.removeFragment(fr: Fragment) {
    val manager = supportFragmentManager
    manager.beginTransaction().remove(fr).commit()
}

inline fun Fragment.setContentFragment(containerViewId: Int, f: () -> Fragment): Fragment? {
    val manager = childFragmentManager
    return f().apply { manager.beginTransaction().replace(containerViewId, this).commit() }
}

fun FragmentActivity.setWhiteStatusBar() {
    val window = window

// clear FLAG_TRANSLUCENT_STATUS flag:
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

// finally change the color
    window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
}

fun FragmentActivity.log_d(mess: String?) {
    Log.d(javaClass.simpleName, mess)
}

fun FragmentActivity.log_d(number: Long) {
    Log.d(javaClass.simpleName, " value = $number")
}

fun FragmentActivity.log_d(number: Long, number2: Long) {
    Log.d(javaClass.simpleName, " value1 = $number \n value2 = $number2")
}

fun FragmentActivity.log_d(number: Long, number2: Long, number3: Long) {
    Log.d(javaClass.simpleName, " value1 = $number, value2 = $number2, value3 = $number3 ")
}

fun FragmentActivity.log_e(mess: String?) {
    Log.e(javaClass.simpleName, mess)
}

fun FragmentActivity.log_i(mess: String?) {
    Log.i(javaClass.simpleName, mess)
}