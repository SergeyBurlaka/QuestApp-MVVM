package com.burlaka.utils.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.replaceFragment(
    fragment: Fragment, id: Int, isBackStack: Boolean,
    allowStateLoss: Boolean = false
) {
    supportFragmentManager.beginTransaction().apply {
        //        setTransition(TRANSIT_FRAGMENT_FADE)
        replace(id, fragment, fragment::class.java.simpleName)
        if (isBackStack) {
            addToBackStack(fragment::class.java.simpleName)
        }
        when {
            !supportFragmentManager.isStateSaved -> commit()
            allowStateLoss -> commitAllowingStateLoss()
        }
//        show(fragment)
    }
}

