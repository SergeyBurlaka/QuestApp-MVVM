package com.burlaka.vmpusher.sample.viewmodel

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

/**
 * @author Sergey
 * @since 06.02.2019
 */
class TimerEngine() {


    var awaitTimeInMillis: Int = 25
        private set

    fun getLockImpulses(updatePeriod: Long = 1_000L): Flowable<LockImpulse> {
        return Flowable.zip(
            Flowable.interval(0L, updatePeriod, TimeUnit.MILLISECONDS).map { it.toInt() },
            Flowable.range(0, awaitTimeInMillis).map { awaitTimeInMillis - it },

            BiFunction<Int, Int, LockImpulse> { timeCount, _ ->
                LockImpulse(
                    timeCount = if (timeCount >= 0) timeCount else 0,
                    isTimeAvailable = timeCount > 0
                )
            })
    }

    companion object {
        data class LockImpulse(
            val isTimeAvailable: Boolean,
            val timeCount: Int
        )
    }

}