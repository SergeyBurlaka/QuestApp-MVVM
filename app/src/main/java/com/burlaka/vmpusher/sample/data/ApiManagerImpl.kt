package com.burlaka.vmpusher.sample.data

import io.reactivex.Single
import java.util.concurrent.TimeUnit


class ApiManagerImpl() : ApiManager {

    override fun phoneRequest(phone: String) = Single.just(true).delay(1_50, TimeUnit.MILLISECONDS)!!
}