package com.burlaka.utils.ext

import com.burlaka.utils.logger.L

/**
 * @author Sergey
 * @since 07.02.2019
 */
fun Any.logD(tag: String, mess: String?) {
    L.d(tag, "$mess")
}

fun Any.logD(tag: String, number: Long) {
    L.d(tag, " value = $number")
}

fun Any.logD(tag: String, number: Long, number2: Long) {
    L.d(tag, " value1 = $number \n value2 = $number2")
}

fun Any.logD(tag: String, number: Long, number2: Long, number3: Long) {
    L.d(tag, " value1 = $number, value2 = $number2, value3 = $number3 ")
}

/**
 *  with throwable
 */
fun Any.logD(tag: String, message: String, e: Throwable) {
    L.d(tag, message, e)
}

fun Any.logE(tag: String, mess: String?) {
    L.e(tag, mess)
}

/**
 *  with throwable
 */
fun Any.logE(tag: String, mess: String?, e: Throwable) {
    L.e(tag, "$mess", e)
}

fun Any.logI(tag: String, mess: String?) {
    L.i(tag, "$mess")
}

/**
 *  with object
 */
fun Any.logI(tag: String, `object`: Any) {
    L.i(tag, `object`)
}

fun Any.logW(tag: String, mess: String?) {
    L.w(tag, "$mess")
}

/**
 * no tag
 */

fun Any.logD(mess: String?) {
    L.d(javaClass.simpleName, "$mess")
}

fun Any.logD(number: Long) {
    L.d(javaClass.simpleName, " value = $number")
}

fun Any.logD(number: Long, number2: Long) {
    L.d(javaClass.simpleName, " value1 = $number \n value2 = $number2")
}

fun Any.logD(number: Long, number2: Long, number3: Long) {
    L.d(javaClass.simpleName, " value1 = $number, value2 = $number2, value3 = $number3 ")
}

/**
 *  with throwable
 */
fun Any.logD(message: String, e: Throwable) {
    L.d(javaClass.simpleName, message, e)
}

fun Any.logE(mess: String?) {
    L.e(javaClass.simpleName, mess)
}

/**
 *  with throwable
 */
fun Any.logE(mess: String?, e: Throwable) {
    L.e(javaClass.simpleName, "$mess", e)
}

fun Any.logI(mess: String?) {
    L.i(javaClass.simpleName, "$mess")
}

/**
 *  with object
 */
fun Any.logI(`object`: Any) {
    L.i(javaClass.simpleName, `object`)
}

fun Any.logW(mess: String?) {
    L.w(javaClass.simpleName, "$mess")
}