package com.burlaka.utils.ext

/**
 * @author Stas
 * @since 2/4/19.
 */
inline fun Boolean.doIfTrue(t:()->Unit){
    if (this) {
        t.invoke()
    }
}

inline fun Boolean.doIfFalse(f: () -> Unit) {
    if (!this){
        f.invoke()
    }
}