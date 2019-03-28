package com.burlaka.utils.logger

import android.os.Build
import android.os.Environment
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Stas
 * @since 2/1/19.
 */
class LogWriter private constructor() {
    private var logFileName = "_logfile.txt"
        set(versionName) {
            field = versionName + this.logFileName
        }

    fun write(tag: String, message: String?, status: String) {
        synchronized(lock) {
            Thread {
                val sb = StringBuilder()
                val milliseconds = System.currentTimeMillis()
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                val resultDate = Date(milliseconds)
                sb.append(sdf.format(resultDate))
                sb.append(SPACE).append(tag).append(SPACE)
                if (message != null) {
                    sb.append(message).append(SPACE)
                }
                sb.append(status).append(SEPARATOR)
                try {
                    val logFile = logFile
                    val bw = BufferedWriter(FileWriter(logFile, true))
                    bw.write(sb.toString())
                    bw.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    companion object {
        private const val SPACE = " "
        private const val NEW_LINE = "\n"

        private val SEPARATOR = (NEW_LINE
                + NEW_LINE
                + "********************" + Build.BRAND
                + " "
                + Build.MODEL
                + "**************************"
                + NEW_LINE
                + NEW_LINE)

        private val lock = Any()
        private var logWriter: LogWriter? = null

        val instance: LogWriter
            get() {
                if (logWriter == null) {
                    logWriter = LogWriter()
                }
                return logWriter!!
            }

        private val logFile: File
            get() {
                var logPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                logPath = File(logPath.absolutePath + "/")
                if (!logPath.exists()) {
                    logPath.mkdirs()
                }
                return File(logPath, instance.logFileName)
            }
    }
}
