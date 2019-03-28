package com.burlaka.utils.ext

import android.content.Context
import android.content.res.Resources
import android.os.Build

/**
 * @author Stas
 * @since 3/11/19.
 */
@Throws(Resources.NotFoundException::class)
fun Resources.color(color: Int, context: Context): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.getColor(color, context.theme)
    } else {
        this.getColor(color)
    }
}