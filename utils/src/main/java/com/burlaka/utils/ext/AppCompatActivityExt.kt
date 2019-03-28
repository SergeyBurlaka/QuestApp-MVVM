package com.burlaka.utils.ext

import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


fun FragmentActivity.showKeyboardExt() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(currentFocus, InputMethodManager.SHOW_FORCED)
}

fun FragmentActivity.hideKeyboardExt() {
    val view = this.currentFocus
    if (view != null) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.hideSoftInputFromWindow(
            view.windowToken, 0
        )
    }
}

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
    //window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
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