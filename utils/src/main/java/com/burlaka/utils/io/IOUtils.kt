package com.burlaka.utils.io


import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import java.io.*
import java.util.*


object IOUtils {

    private val TAG = IOUtils::class.java.simpleName

    fun getUriFromPath(context: Context, path: String, providerAuth: String): Uri {
        return FileProvider.getUriForFile(context, providerAuth, File(path))
    }

    fun getRealPathFromUri(uri: Uri, projectionPath: String, context: Context): String? {
        context.contentResolver.query(
            uri, arrayOf(projectionPath), null, null, null
        )?.use { cursor ->
            val columnIndex: Int = cursor.getColumnIndexOrThrow(projectionPath)
            cursor.moveToFirst()
            val s = cursor.getString(columnIndex)
            cursor.close()
            return s
        }
        return uri.path
    }

    fun getBytesFromFile(filePath: String): ByteArray {
        val file = File(filePath)
        val size = file.length().toInt()
        val bytes = ByteArray(size)

        try {
            FileInputStream(file).use { fis -> BufferedInputStream(fis).use { buf -> buf.read(bytes, 0, bytes.size) } }
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "Failed reading file:" + e.message)
        } catch (e: IOException) {
            Log.e(TAG, "Failed reading file: " + e.message)
        }

        return bytes
    }

    fun getMimeType(url: String?): String {
        if (url == null) return ""
        var type: String? = null

        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase())
        }
        if (type == null) {
            type = "image/*" // fallback method_type. You might set it to */*
        }
        return type
    }

    fun humanReadableByteCount(bytes: Long, si: Boolean): String {
        val unit = if (si) 1000 else 1024
        if (bytes < unit) return bytes.toString() + " B"
        val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
        val pre = (if (si) "kMGTPE" else "KMGTPE")[exp - 1] + if (si) "" else "i"
        return String.format(Locale.getDefault(), "%.1f %sB", bytes / Math.pow(unit.toDouble(), exp.toDouble()), pre)
    }
}
