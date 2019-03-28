package com.burlaka.vmpusher

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @author Sergey
 * @since 12.02.2019
 */
interface INavigate {

    fun AppCompatActivity.navigateBind(baseNavigator: NavigateVM.Companion.BaseNavigator) {
        getNavigate()?.let { f ->
            baseNavigator.vector.observe(this, Observer {
                getSingleResult(it)?.let { navigateId ->
                    f.invoke(navigateId)
                }
            })
        }
    }

    infix fun AppCompatActivity.receivePushesFrom(navigate: NavigateVM): NavigateVM {
        getNavigate()?.let { f ->
            navigate.getBaseNavigator().vector.observe(this, Observer<NavigateEvent<Int>> {
                this.getSingleResult(it)?.let { navigateId ->
                    f.invoke(navigateId)
                }
            })
        }
        return navigate
    }

    /**
     * Override for add action navigation function
     * @return navigate func for [INavigate] listener via id
     */
    fun getNavigate(): ((id: Int) -> Unit)?
}

interface NavigateVM {

    fun getBaseNavigator(): BaseNavigator

    companion object {
        class BaseNavigator {

            val vector: LiveData<NavigateEvent<Int>>
                get() = _vector
            private var _vector = MutableLiveData<NavigateEvent<Int>>()

            fun navigateTo(id: Int): Int {
                _vector.value = NavigateEvent(id)
                return id
            }

            fun onBack() = getCash().run {
                if (this == null) false else {
                    _vector.value = this
                    true
                }
            }

            fun cache(id: Int): Int {
                if (navigateCash.isNotEmpty()) {
                    if (navigateCash[0] == id) {
                        return id
                    }
                }
                navigateCash.add(0, id)
                return id
            }

            fun clearCash() {
                navigateCash.clear()
            }

            fun restoreFromCash(defoltActionId: Int) {
                if (_vector.value == null) {
                    navigateTo(defoltActionId).casheId()
                } else {
                    _vector.value!!.peekContent().navigateToById()
                }
            }

            private fun Int.navigateToById(): Int {
                _vector.value = NavigateEvent(this)
                return this
            }

            fun getCash(): NavigateEvent<Int>? {
                return navigateCash.run {
                    previous().let {
                        if (it != null) {
                            NavigateEvent(it.apply {
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
            private val navigateCash = ArrayList<Int>()

            private fun ArrayList<Int>.previous(): Int? {
                return if (isNotEmpty()) {
                    removeAt(0)
                    if (isNotEmpty()) {
                        get(0)
                    } else null
                } else null
            }
        }
    }

}

