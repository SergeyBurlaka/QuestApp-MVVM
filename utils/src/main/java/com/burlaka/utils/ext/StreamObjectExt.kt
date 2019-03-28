package com.burlaka.utils.ext

/**
 * Created by Sergey
 * @since 13.03.19
 */


inline fun <P,T> P.map(f: (profile: P) -> T): T {
    return f.invoke(this)
}

