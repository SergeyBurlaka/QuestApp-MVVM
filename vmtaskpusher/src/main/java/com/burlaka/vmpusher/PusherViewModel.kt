package com.burlaka.vmpusher

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel

/**
 *  Upgrade you ViewModel to Presenter. Revolution comes.
 *
 *  How does Google ViewModel and LiveData upgrade?
 *  Hm, sometime I want to call method from view. Because it is very useful! For navigation eg.
 *
 *  To use LiveData you need to subscribe the data - activity must to implement observer callback. Not so simple i say.
 *  You can simple add Activity context to ViewModel. But i doesn't think it is good idea!
 *  You can use weak reference but it isn't the best idea!
 *
 *  I want to present VmTaskPusher. It helps to push you task to Activity by live data but with some Live Data Implement encapsulation.
 *  With VmTaskPusher You just calls the methods and that all.
 *
 *  Funny, but ViewModel via VmTaskPusher becomes the Presenter in some way. Use MVVM and MVP abilities in some way at the same time!
 *  In the end You get Model-View-Presenter-View-Model (MVPVM) or Upgraded View Model. Joke ;) As for me, funny to call it "PresenterVieModel" ;)
 *
 *  So, as early You can subscribe to ViewModel LiveData from Activity,
 *  but now You can simply calling methods from ViewModel to Activity pushing You task via VmTaskPusher,
 *  like ViewModel have become the Presenter.
 *
 *  You know, I think it is little crazy but to match usefully in some cases.
 *  For navigation eg. //todo describe sample with navigation
 */
abstract class PusherViewModel :
    ViewModel(),
    VmTaskPusher {

    val taskNotificator: TaskNotificatorCenter = TaskNotificatorCenter()
    override fun getBaseNavigator() = taskNotificator
    infix fun Int.cashTo(pusher: TaskNotificatorCenter) = pusher.cache(this)
    infix fun Int.pushBy(pusher: TaskNotificatorCenter) = Handler(Looper.getMainLooper()).post { pusher.pushTaskById(this) }

    override fun toString(): String {
        return "view model with type ${this::class.java.simpleName}"
    }
}