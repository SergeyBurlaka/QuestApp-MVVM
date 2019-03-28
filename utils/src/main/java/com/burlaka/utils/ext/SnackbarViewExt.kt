package com.burlaka.utils.ext

import android.view.View
import android.widget.TextView
import androidx.annotation.Nullable
import com.burlaka.utils.R
import com.google.android.material.snackbar.Snackbar

/**
 * Simple [Snackbar]
 *
 * Example: mView.snackAlert("Alert", Color.RED, { v -> v.requestFocus() } )
 *
 * @param message Main text
 * @param color @Nullable background color
 * @param textColor @Nullable text color
 * @param f Unit function with caller [View]
 */
inline fun View.snackAlert(
    message: String, @Nullable color: Int? = null, @Nullable textColor: Int? = null,
    f: Snackbar.(view: View) -> Unit = {}
) {
    var snack = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    if (!snack.isShownOrQueued) {
        snack = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        color?.let { snack.view.setBackgroundColor(color) }
        textColor?.let { snack.setActionTextColor(textColor) }
        snack.f(this)
        snack.show()
    }
}

/**
 * [Snackbar] with action
 *
 * Example: mView.snackAction("Ok", "Dismiss previous", Color.RED, Color.BLACK, {}, { //Ok pressed })
 *
 * @param action Action button text
 * @param message Main text
 * @param color @Nullable background color
 * @param textColor @Nullable text color
 * @param f Unit function caller [View]
 * @param listener Unit callback when action pressed
 */
inline fun View.snackAction(
    action: String,
    message: String, @Nullable color: Int? = null, @Nullable textColor: Int? = null,
    actionColor: Int? = null,
    f: Snackbar.(view: View) -> Unit = {},
    noinline listener: (View) -> Unit = {}
) {
    var snack = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    if (!snack.isShownOrQueued) {
        snack = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        snack.setAction(action, listener)
        color?.let { snack.view.setBackgroundColor(color) }
        actionColor?.let { snack.setActionTextColor(actionColor) }
        textColor?.let { snack.view.findViewById<TextView>(R.id.snackbar_text).setTextColor(textColor) }
        snack.f(this)
        snack.show()
    }
}

