package com.burlaka.vmpusher.sample.viewmodel

import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.burlaka.utils.ext.logD
import com.burlaka.utils.ext.logE
import com.burlaka.vmpusher.PusherViewModel
import com.burlaka.vmpusher.TaskExecutable
import com.burlaka.vmpusher.sample.R
import com.burlaka.vmpusher.sample.viewmodel.MainViewModel.TestData.A
import com.burlaka.vmpusher.sample.viewmodel.MainViewModel.TestData.B
import com.burlaka.vmpusher.sample.viewmodel.MainViewModel.TestData.C
import com.burlaka.vmpusher.sample.viewmodel.MainViewModel.TestData.questions
import com.burlaka.vmpusherannotation.BindUiAction
import com.burlaka.vmpusherannotation.BindUiListener
import com.jellyworkz.processor.MainView.showFirstQuestionForMainView
import com.jellyworkz.processor.MainView.startClockForMainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers


class MainViewModel(private val timerEngine: TimerEngine) : PusherViewModel() {

    val questionLiveData: LiveData<TestData.Question> get() = _questionLiveData
    val timerLiveData: LiveData<String> get() = _timerLiveData
    private var mCurrentAnswer = -1

    fun startTimer() {
        disposable.dispose()
        disposable =
                timerEngine.getLockImpulses()
                    .map { it.timeCount }
                    .map { "$it" }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        startClockForMainView() pushBy taskNotificator
                        showFirstQuestionForMainView() pushBy taskNotificator
                    }
                    .subscribe({
                        _timerLiveData.value = it
                    }, {
                        this@MainViewModel.logE("Failed: ${it.message}")
                    })

    }

    fun onCheckedChanged(viewGroup: RadioGroup, id: Int) {
        when (id) {
            R.id.radioButton -> {
                mCurrentAnswer = A
                this@MainViewModel.logD("A")
            }

            R.id.radioButton2 -> {
                mCurrentAnswer = B
                this@MainViewModel.logD("B")
            }

            R.id.radioButton3 -> {
                mCurrentAnswer = C
                this@MainViewModel.logD("C")
            }
        }
    }

    companion object {
        @BindUiListener
        interface MainView : TaskExecutable {

            @BindUiAction(actionId = 5)
            fun startClock()

            @BindUiAction(actionId = 1)
            fun showFirstQuestion()
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    private val _timerLiveData = MutableLiveData<String>()
    private var disposable = Disposables.empty()

    object TestData {
        data class Question(
            val id: Int,
            val questionText: String,
            val variations: ArrayList<String>,
            val answer: Int
        )

        const val A = 0
        const val B = 1
        const val C = 2

        val questions = arrayListOf(
            Question(
                id = 1,
                questionText = "What is Android?",
                variations = arrayListOf("Operation system", "Sweetness", "Mythical humanoid creature "),
                answer = A
            ),
            Question(
                id = 2,
                questionText = "2 + 2 = ?",
                variations = arrayListOf("5", "3", "4"),
                answer = C
            )
        )
    }

    private val _questionLiveData = MutableLiveData<TestData.Question>().apply {
        value = questions[0]
    }
}