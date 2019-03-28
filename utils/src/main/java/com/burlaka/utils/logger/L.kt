package com.burlaka.utils.logger

import com.burlaka.utils.BuildConfig


/**
 * @author Stas
 * @since 30.01.2019.
 */

object L {

    private val isLoggingEnabled = BuildConfig.DEBUG
    private val canWriteLogs = false

    @JvmStatic
    fun d(TAG: String, message: String) {
        if (isLoggingEnabled) {
            writeLog(TAG, message, "D")
            android.util.Log.d(TAG, message)
        }
    }

    @JvmStatic
    fun d(TAG: String, message: String, e: Throwable) {
        if (isLoggingEnabled) {
            writeLog(TAG, message, "E")
            android.util.Log.d(TAG, message, e)
        }
    }

    @JvmStatic
    fun i(TAG: String, message: String) {
        if (isLoggingEnabled) {
            writeLog(TAG, message, "I")
            android.util.Log.i(TAG, message)
        }
    }

    @JvmStatic
    fun i(TAG: String, `object`: Any) {
        if (isLoggingEnabled) {
            writeLog(TAG, `object`.toString(), "I")
            android.util.Log.i(TAG, `object`.toString())
        }
    }

    @JvmStatic
    fun w(TAG: String, message: String?) {
        if (isLoggingEnabled) {
            writeLog(TAG, message, "W")
            android.util.Log.w(TAG, message)
        }
    }

    @JvmStatic
    fun w(TAG: String, message: String?, e: Throwable) {
        if (isLoggingEnabled) {
            writeLog(TAG, message, "W")
            android.util.Log.w(TAG, message, e)
        }
    }

    @JvmStatic
    fun e(TAG: String, message: String?) {
        if (isLoggingEnabled) {
            writeLog(TAG, message, "E")
            android.util.Log.e(TAG, message)
        }
    }

    @JvmStatic
    fun e(TAG: String, message: String, throwable: Throwable) {
        if (isLoggingEnabled) {
            writeLog(TAG, message, "E")
            android.util.Log.e(TAG, message, throwable)
        }
    }

    private fun writeLog(tag: String, message: String?, status: String) {
        if (canWriteLogs) {
            LogWriter.instance.write(tag, message, status)
        }
    }
}
