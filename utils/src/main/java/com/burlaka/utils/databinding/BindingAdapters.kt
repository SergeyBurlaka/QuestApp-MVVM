package com.burlaka.utils.databinding

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

import androidx.lifecycle.MutableLiveData
import com.burlaka.utils.font.CustomFontFamily
import com.burlaka.utils.img.ImageUtil
import com.burlaka.utils.ext.setDisabledDoubleClickListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout


/**
 * @author Stas
 * @since 8/17/18.
 */
@BindingAdapter("android:addTextWatcher")
fun addTextWatcher(view: EditText, watcher: TextWatcher?) {
    view.addTextChangedListener(watcher)
}

@BindingAdapter("android:addTextWatcher")
fun addTextWatcher(view: EditText, watcher: MutableLiveData<String>?) {
    val watch = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            watcher?.value = s.toString()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
    view.addTextChangedListener(watch)
}

@BindingAdapter("android:showError")
fun showError(view: TextInputLayout, error: String?) {
    view.error = error
}

@BindingAdapter("android:focus")
fun focus(view: EditText, focus: MutableLiveData<Boolean>?) {
    if (focus?.value != null && focus.value!!) {
        view.requestFocus()
        focus.postValue(null)
    }
}

@BindingAdapter("android:srcImg")
fun setImageUri(view: ImageView, imageUri: Uri) {
    view.setImageURI(imageUri)
}

@BindingAdapter("android:srcImg")
fun setImgDrawable(view: ImageView, drawable: Drawable) {
    view.setImageDrawable(drawable)
}

@BindingAdapter("android:srcImg")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("android:srcUrl")
fun setImageResourceUrl(imageView: ImageView, url: String?) {
    url?.let { u ->
        ImageUtil.loadPhoto(u, imageView)
//        fromUrlUiThread(u) {
//            imageView.setImageBitmap(it)
//        }
    }
}

@BindingAdapter("android:srcCashed")
fun setImageResourceCashed(imageView: ImageView, url: String) {
    ImageUtil.loadCashedPhoto(url, imageView)
}


@BindingAdapter("android:background")
fun setBackground(imageView: ImageView, bg: Int?) {
    bg?.let {
        imageView.setBackgroundColor(bg)
    }
}

@BindingAdapter("android:text")
fun setText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("android:background")
fun setBackgroundResource(imageView: ImageView, resource: Int) {
    imageView.setBackgroundResource(resource)
}

@BindingAdapter("android:backgroundColor")
fun setBackgroundColor(imageView: ImageView, color: Int) {
    imageView.setBackgroundColor(color)
}

@BindingAdapter("android:background")
fun setBackgroundDrawable(imageView: ImageView, drawable: Drawable) {
    imageView.background = drawable
}

@BindingAdapter("android:onClick")
fun setOnClickListener(view: View, listener: View.OnClickListener) {
    view.setDisabledDoubleClickListener {
        listener.onClick(it)
    }
}

@BindingAdapter("android:fabText")
fun setFabText(view: FloatingActionButton, text: String?) {
    val paint = Paint(ANTI_ALIAS_FLAG)
    paint.textSize = 40f
    paint.flags = Paint.FAKE_BOLD_TEXT_FLAG
   // paint.color = fabColor
    paint.textAlign = Paint.Align.LEFT

    val string = text ?: " "

    val baseline = -paint.ascent() // ascent() is negative
    val width = (paint.measureText(string) + 0.0f).toInt() // round
    val height = (baseline + paint.descent() + 0.0f).toInt()
    val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(image)
    canvas.drawText(string, 0f, baseline, paint)
    view.setImageBitmap(image)
}

@BindingAdapter("android:backgroundObserver")
fun bacgroundObserver(view: Button, change: Boolean) {
    val bg = view.background as GradientDrawable
    if (change) {
        bg.setColor(Color.parseColor("#ffef412c"))
        view.setTextColor(Color.parseColor("#ffffff"))

    } else {
        view.setTextColor(Color.parseColor("#ff7f8385"))
        bg.setColor(Color.parseColor("#ffeaebeb"))
    }
}

@BindingAdapter("android:setFont")
fun setFont(v: TextView, font: String) {
    v.typeface = CustomFontFamily.instance.getFont(font, v.context)
}

@BindingAdapter("android:setFont")
fun setFont(v: Button, font: String) {
    v.typeface = CustomFontFamily.instance.getFont(font, v.context)
}

@BindingAdapter("android:setFont")
fun setFont(v: EditText, font: String) {
    v.typeface = CustomFontFamily.instance.getFont(font, v.context)
}

