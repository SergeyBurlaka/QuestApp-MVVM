package com.burlaka.utils.ext

import android.content.res.Resources
import java.util.concurrent.TimeUnit

/**
 * @author Sergey
 * @since 06.02.2019
 */
fun Int.pxToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density).toInt()
}

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun Long.getTime() = String.format(
    "%02d min, %02d sec",
    TimeUnit.MILLISECONDS.toMinutes(this),
    TimeUnit.MILLISECONDS.toSeconds(this) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this))
)
