package com.burlaka.utils.ext

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.ContextWrapper
import android.os.Build
import android.text.Editable
import android.text.Html
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Pattern


//private val NAME_VALIDATOR = Pattern.compile("^\\p{L}{1,20}$")
private val NAME_VALIDATOR = Pattern.compile("^.*$")
private val SPACE_VALIDATOR = Pattern.compile("^[^\\s]+(\\s+[^\\s]+)*\$")
private val EMAIL_VALIDATOR = Patterns.EMAIL_ADDRESS
private val PASSWORD_VALIDATOR = Pattern.compile("^(?=.*[0-9]).{6,}$")
private val AGE_VALIDATOR = Pattern.compile("^[1-9][0-9]?$|^100$")

fun EditText.validateName(): Boolean {
    return validator(this.text, NAME_VALIDATOR)
}

fun EditText.validQuery(): Boolean {
    return validator(this.text, SPACE_VALIDATOR)
}

fun EditText.validateEmail(): Boolean {
    return validator(this.text, EMAIL_VALIDATOR)
}

fun String?.validateEmail(): Boolean {
    return validator(this, EMAIL_VALIDATOR)
}

fun String?.validatePassword(): Boolean {
    return validator(this, PASSWORD_VALIDATOR)
}

fun String?.validateName(): Boolean {
    return validator(this, NAME_VALIDATOR)
}

fun String?.validateAge(): Boolean {
    return validator(this, AGE_VALIDATOR)
}

private fun validator(data: Editable?, pattern: Pattern): Boolean {
//    if (TextUtils.isEmpty(data)) return false
    return pattern.matcher(data).matches()
}

private fun validator(data: String?, pattern: Pattern): Boolean {
    if (data.isNullOrEmpty()) return false
    return pattern.matcher(data).matches()
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun View.setGone() {
    this.visibility = View.GONE
}

fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.setSelected() {
    this.isSelected = true
}

fun View.setUnSelected() {
    this.isSelected = false
}

fun TextView.setTextFromHtml(text: String) {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    } else {
        this.text = Html.fromHtml(text)
    }
}

/**
 * Return color with API check
 */
fun View.getColor(color: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.context.resources.getColor(color, context.theme)
    } else {
        this.context.resources.getColor(color)
    }
}

fun EditText.showKeyboard() {
    if (requestFocus()) {
        (getActivity()?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(this, SHOW_IMPLICIT)
        setSelection(text.length)
    }
}

fun View.getActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}
