package com.burlaka.utils.ext

import android.content.SharedPreferences


fun SharedPreferences.add(key: String, value: Any) {
    val editor = this.edit()
    when (value) {
        is String -> editor.putString(key, value)
        is Boolean -> editor.putBoolean(key, value)
        is Int -> editor.putInt(key, value)
        is Long -> editor.putLong(key, value)
        is Set<*> -> editor.putStringSet(key, value as Set<String>)
    }
    editor.apply()
}

fun SharedPreferences.commit(key: String, value: Any) {
    val editor = this.edit()
    when (value) {
        is String -> editor.putString(key, value)
        is Boolean -> editor.putBoolean(key, value)
        is Int -> editor.putInt(key, value)
        is Long -> editor.putLong(key, value)
        is Set<*> -> editor.putStringSet(key, value as Set<String>)
    }
    editor.commit()
}

fun SharedPreferences.delete(key: String) {
    this.edit().remove(key).apply()
}
