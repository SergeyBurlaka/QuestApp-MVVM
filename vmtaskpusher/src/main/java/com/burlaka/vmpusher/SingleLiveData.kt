package com.burlaka.vmpusher

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class PushingTask<out T>(
    private val content: T
    , val massage: String? = null
) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

fun <T> PushingTask<T>.getCash() =
    PushingTask(this.peekContent())

fun <T> Fragment.getSingleResult(event: PushingTask<T>?): T? {
    return if (event != null && event.hasBeenHandled.not()) {
        event.getContentIfNotHandled()
    } else null
}

fun <T> AppCompatActivity.getSingleResult(event: PushingTask<T>?): T? {
    return if (event != null && event.hasBeenHandled.not()) {
        event.getContentIfNotHandled()
    } else null
}