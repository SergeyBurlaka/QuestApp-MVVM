package com.burlaka.utils.ext

import io.reactivex.Single
import io.reactivex.disposables.Disposable

/**
 * @author BRCJU
 * @since 28.03.2019
 */

fun <T> Single<T>.logError(any: Any): Single<T> {
    return this.doOnError {
        any.logE("Failed operation: ${it.message}")
    }
}

fun Disposable?.checkIfNotDispose() = this != null && this.isDisposed.not()