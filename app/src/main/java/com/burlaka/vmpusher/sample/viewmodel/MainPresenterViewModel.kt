package com.burlaka.vmpusher.sample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.burlaka.utils.ext.logE
import com.burlaka.vmpusher.BasePresenterViewModel
import com.burlaka.vmpusher.TaskExecutable
import com.burlaka.vmpusherannotation.BindUiAction
import com.burlaka.vmpusherannotation.BindUiListener
import com.jellyworkz.processor.MainView.startClockForMainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers


class MainPresenterViewModel(private val timerEngine: TimerEngine) : BasePresenterViewModel() {

    val timer: LiveData<String>
        get() = _timer

    fun startTimer() {
        disposable =
                timerEngine.getLockImpulses()
                    .map { it.timeCount }
                    .map { "$it" }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        disposable.dispose()
                        startClockForMainView() pushBy taskPusher
                    }
                    .subscribe({
                        _timer.value = it
                    }, {
                        this@MainPresenterViewModel.logE("Failed: ${it.message}")
                    })

    }

    companion object {
        @BindUiListener
        interface MainView : TaskExecutable {

            @BindUiAction(actionId = 5)
            fun startClock()
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    private val _timer = MutableLiveData<String>()
    private var disposable = Disposables.empty()
}