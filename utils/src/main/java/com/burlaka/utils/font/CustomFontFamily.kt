package com.burlaka.utils.font

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import java.util.*

class CustomFontFamily {

    private var fontMap = HashMap<String, String>()

    fun addFont(alias: String, fontName: String) {
        fontMap[alias] = fontName
    }

    fun getFont(alias: String, context: Context): Typeface? {
        val fontFilename = fontMap[alias]
        return if (fontFilename == null) {
            Log.e("FONT", "Font not available with fieldObserver $alias")
            null
        } else {
            Typeface.createFromAsset(context.assets,
                    "fonts/$fontFilename")
        }
    }

    companion object {
        private var customFontFamily: CustomFontFamily? = null

        val instance: CustomFontFamily
            get() {
                if (customFontFamily == null)
                    customFontFamily =
                            CustomFontFamily()


                return customFontFamily!!
            }
    }
}