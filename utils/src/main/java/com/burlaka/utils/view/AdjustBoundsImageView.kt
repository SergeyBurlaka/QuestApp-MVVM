package com.burlaka.utils.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView

/**
 * @author Stas
 * @since 3/7/19.
 */
class AdjustBoundsImageView : AppCompatImageView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        if (drawable == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }

        super.onMeasure(widthMeasureSpec
            , View.MeasureSpec.makeMeasureSpec(Math.round(View.MeasureSpec.getSize(widthMeasureSpec).toFloat() / (drawable.intrinsicWidth.toFloat() / drawable.intrinsicHeight.toFloat()))
                , View.MeasureSpec.EXACTLY))
    }
}