package com.burlaka.utils.img

import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * @author Stas
 * @since 4/13/18.
 */
class CircleTransform(private val bgColor: Int) : BitmapTransformation() {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {

        val source = if (toTransform.width > outWidth) {
            Bitmap.createScaledBitmap(toTransform
                    , outWidth
                    , outHeight, false)
        } else {
            toTransform
        }

        return circleCropWithBorder(pool, source)
    }

    private fun circleCrop(pool: BitmapPool, source: Bitmap): Bitmap {

        val size = Math.min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        val squared = Bitmap.createBitmap(source, x, y, size, size)

        var result: Bitmap? = pool.get(size, size, Bitmap.Config.ARGB_8888)
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(result!!)
        val paint = Paint()
        paint.shader = BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)

        return result
    }

    private fun circleCropWithBorder(pool: BitmapPool, source: Bitmap): Bitmap {

        val size = Math.min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2
        val squared = Bitmap.createBitmap(source, x, y, size, size)

        var result: Bitmap? = pool.get(size, size, Bitmap.Config.ARGB_8888)
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(result!!)
        val paint = Paint()
        val whitePaint = Paint()
        whitePaint.isAntiAlias = true
        whitePaint.color = bgColor
        val radius = size / 2f
        canvas.drawCircle(radius, radius, radius, whitePaint)
        paint.shader = BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        paint.color = Color.WHITE
        val r = size / 2.2f
        canvas.drawCircle(radius, radius, r, paint)

        return result
    }

}
