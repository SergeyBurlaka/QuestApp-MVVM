package com.burlaka.utils.ext

import android.os.SystemClock
import android.view.View

/**
 * @author Stas
 * @since 5/16/18.
 */
private var mLastClickTime = 0L
private const val DOUBLE_CLICK_TIME = 300L

fun View.setDisabledDoubleClickListener(view: (View) -> Unit) {
    this.setOnClickListener {
        if (SystemClock.elapsedRealtime() - mLastClickTime > DOUBLE_CLICK_TIME) {
            mLastClickTime = SystemClock.elapsedRealtime()
            view.invoke(it)
        }
    }

}
