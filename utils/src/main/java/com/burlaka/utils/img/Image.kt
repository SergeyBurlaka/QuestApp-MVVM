package com.burlaka.utils.img

import android.graphics.Bitmap
import android.os.Environment
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.burlaka.utils.R
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

/**
 * @author Sergey
 * @since 06.02.2019
 */
object ImageUtils {

    /**
     * Loads image from uri to ImageView with placeHolder
     *
     * @param imageView Instance of [ImageView] where you want set an Image
     * @param imageUri  Uri to the image
     */
    @JvmStatic
    fun loadImage(imageView: ImageView?, imageUri: String?, placeholder: Int) {
        if (imageView == null || imageUri == null || imageUri.isEmpty()) {
            return
        }

        val options = RequestOptions()
            //.centerCrop()
            //.override(RESIZE_MEDIUM_WIDTH,RESIZE_MEDIUM_HEIGHT)
            .placeholder(placeholder).error(placeholder).format(DecodeFormat.PREFER_ARGB_8888)
            .override(Target.SIZE_ORIGINAL)
        // .priority(Priority.HIGH)

        Glide.with(imageView.context).load(imageUri).apply(options).into(imageView)

    }

    @JvmStatic
    fun loadImage(imageView: ImageView?, imageUri: String?) {
        if (imageView == null || imageUri == null || imageUri.isEmpty()) {
            return
        }

        val options = RequestOptions()
            //.centerCrop()
            //.override(RESIZE_MEDIUM_WIDTH,RESIZE_MEDIUM_HEIGHT)
            .error(R.drawable.image_placeholder).format(DecodeFormat.PREFER_ARGB_8888).override(Target.SIZE_ORIGINAL)
        // .priority(Priority.HIGH)

        Glide.with(imageView.context).load(imageUri).apply(options).into(imageView)
    }

    @JvmStatic
    fun loadImageNoPlaceholder(imageView: ImageView?, imageUri: String?) {
        if (imageView == null || imageUri == null || imageUri.isEmpty()) {
            return
        }

        val options = RequestOptions().priority(Priority.HIGH)

        Glide.with(imageView.context).load(imageUri).apply(options).into(imageView)
    }

    @Throws(RuntimeException::class)
    fun saveBitmap(bitmap: Bitmap, name: String): File {
        val toWriteSd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File(toWriteSd, "$name.jpg")
        try {
            FileOutputStream(file).use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            }
            return file
        } catch (e: FileNotFoundException) {
            throw RuntimeException("We're Sorry, But failed to load image. Please try again")
        } catch (e: IOException) {
            throw RuntimeException("exception on upload image ${e.message}")
        }
    }
}
