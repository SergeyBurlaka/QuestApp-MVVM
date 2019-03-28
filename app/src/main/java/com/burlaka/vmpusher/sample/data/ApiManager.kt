package com.burlaka.vmpusher.sample.data

import io.reactivex.Single


interface ApiManager {

    fun phoneRequest(phone: String): Single<Boolean>

}