package com.burlaka.utils.ext

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Fragment.toast(message: CharSequence) =
    this.activity?.let {
        Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
    }

