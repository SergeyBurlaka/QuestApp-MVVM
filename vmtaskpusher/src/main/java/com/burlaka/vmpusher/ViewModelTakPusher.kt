package com.burlaka.vmpusher

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @author Sergey
 * @since 12.02.2019
 */
interface TaskExecutable {

    fun AppCompatActivity.navigateBind(basePusher: BasePusher) {
        vmPushExcutable.let { f ->
            basePusher.taskLiveData.observe(this, Observer {
                getSingleResult(it)?.let { navigateId ->
                    f.invoke(navigateId)
                }
            })
        }
    }

    infix fun AppCompatActivity.receiveTaskFrom(navigate: VmTaskPusher): VmTaskPusher {
        vmPushExcutable.let { f ->
            navigate.getBaseNavigator().taskLiveData.observe(this, Observer<PushingTask<Int>> {
                this.getSingleResult(it)?.let { navigateId ->
                    f.invoke(navigateId)
                }
            })
        }
        return navigate
    }

    /**
     * Override for add action navigation function
     * @return navigate func for [TaskExecutable] listener via id
     */
    val vmPushExcutable: ((id: Int) -> Unit)
}

interface VmTaskPusher {

    fun getBaseNavigator(): BasePusher

}

class BasePusher {

    val taskLiveData: LiveData<PushingTask<Int>>
        get() = _task
    private var _task = MutableLiveData<PushingTask<Int>>()

    fun pushTaskById(id: Int): Int {
        _task.value = PushingTask(id)
        return id
    }

    fun onBack() = getCash().run {
        if (this == null) false else {
            _task.value = this
            true
        }
    }

    fun cache(id: Int): Int {
        if (taskCash.isNotEmpty()) {
            if (taskCash[0] == id) {
                return id
            }
        }
        taskCash.add(0, id)
        return id
    }

    fun clearCash() {
        taskCash.clear()
    }

    fun restoreFromCash(defoltActionId: Int) {
        if (_task.value == null) {
            pushTaskById(defoltActionId).casheId()
        } else {
            _task.value!!.peekContent().navigateToById()
        }
    }

    private fun Int.navigateToById(): Int {
        _task.value = PushingTask(this)
        return this
    }

    fun getCash(): PushingTask<Int>? {
        return taskCash.run {
            previous().let {
                if (it != null) {
                    PushingTask(it.apply {
                    })
                } else null
            }
        }
    }

    private fun Int.casheId(): Int {
        cache(this)
        return this
    }

    /**
     * VM navigate cache
     */
    private val taskCash = ArrayList<Int>()

    private fun ArrayList<Int>.previous(): Int? {
        return if (isNotEmpty()) {
            removeAt(0)
            if (isNotEmpty()) {
                get(0)
            } else null
        } else null
    }
}


