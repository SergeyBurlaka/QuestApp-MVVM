package com.burlaka.utils.img

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

/**
 * @author Stas
 * @since 4/13/18.
 */
@GlideModule
class ImageUtil : AppGlideModule() {
    companion object {

        fun loadPhoto(url: String?, imageView: ImageView) {
            GlideApp.with(imageView.context.applicationContext).load(url)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA)).into(imageView)
        }

        fun loadBitmap(bitmap: Bitmap, imageView: ImageView) {
            GlideApp.with(imageView).load(bitmap)
                .into(imageView)

        }

        fun loadCashedPhoto(url: String?, imageView: ImageView) {
            GlideApp.with(imageView)
                .load(url)
                .onlyRetrieveFromCache(true)
                .into(imageView)
        }

        fun loadPhoto(
            context: Context,
            url: String,
            width: Int = Target.SIZE_ORIGINAL,
            height: Int = Target.SIZE_ORIGINAL
        ): Bitmap {
            return GlideApp.with(context).asBitmap().load(url).submit(width, height).get()
        }

        fun loadPhoto(uri: Uri?, imageView: ImageView) {
            //        GlideApp.clear(imageView)
            GlideApp.with(imageView.context.applicationContext).load(uri)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA))
                .into(imageView)
        }

        fun loadCirclePhoto(
            url: String,
            imageView: ImageView,
            bgColor: Int = Color.WHITE,
            overWidth: Int = 100,
            overHeight: Int = 100
        ) {
            //        GlideApp.clear(imageView)
            GlideApp.with(imageView.context).load(url).apply(
                RequestOptions().transform(CircleTransform(bgColor))
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .override(overWidth, overHeight)
            ).into(imageView)
        }

        fun loadCirclePhoto(url: String, imageView: ImageView, bgColor: Int = Color.WHITE) {
            //        GlideApp.clear(imageView)
            GlideApp.with(imageView.context)
                .load(url)
                .apply(RequestOptions().transform(CircleTransform(bgColor)))
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(imageView)
        }

        fun loadCirclePhoto(uri: Uri, imageView: ImageView, bgColor: Int = Color.WHITE) {
            GlideApp.with(imageView.context.applicationContext).load(uri)
                .apply(RequestOptions().transform(CircleTransform(bgColor)).diskCacheStrategy(DiskCacheStrategy.DATA))
                .into(imageView)
        }

    }

}