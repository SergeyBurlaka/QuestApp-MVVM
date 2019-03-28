package com.burlaka.vmpusher.sample.presentation.base

import android.graphics.Color
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import com.burlaka.utils.ext.hideKeyboardExt
import com.burlaka.utils.ext.showKeyboardExt
import com.burlaka.utils.ext.snackAlert
import com.burlaka.vmpusher.TaskExecutable
import com.burlaka.vmpusher.BasePresenterViewModel


abstract class BaseActivity<T : ViewDataBinding> :
    AppCompatActivity(),
    LifecycleObserver,
    FragmentHost,
    TaskExecutable {

    /**
     * Performing dependency injection and data binding
     * Note!!!
     * Do not use [setContentView]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this@BaseActivity, layoutId())
        getBaseViewModel()?.activityBind()?.dataBind()
    }

    /**
     * Data binding
     */
    private var viewDataBinding: T? = null

    fun getViewDataBinding(): T {
        return viewDataBinding!!
    }

    /**
     * VM
     */
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var baseViewModel: BasePresenterViewModel? = null

    /**
     * Base VM activityBind
     * @return base VM if exist
     */
    abstract fun getBaseViewModel(): BasePresenterViewModel?

    /**
     * Override for set binding variable
     * @return variable id
     */
    abstract fun bindingVmVariable(): Int?

    private fun BasePresenterViewModel.dataBind(): BasePresenterViewModel {
        viewDataBinding?.let {
            it.setVariable(bindingVmVariable()!!, this)
            it.executePendingBindings()
        }
        return this
    }

    private fun BasePresenterViewModel.activityBind(): BasePresenterViewModel {
        if (baseViewModel == null) {
            baseViewModel = this
        }
        viewModelRegistration.add(this)
        return this
    }

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun layoutId(): Int

    override fun hideKeyboard() {
        this@BaseActivity.hideKeyboardExt()
    }

    override fun showKeyboard() {
        this@BaseActivity.showKeyboardExt()
    }

    override fun returnBack() {
        onBackPressed()
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    protected fun showFailedMess(errorMess: String) {
        hideKeyboard()
        getViewDataBinding().root.snackAlert(errorMess, Color.RED)
    }

    protected fun showMessage(errorMess: String) {
        hideKeyboard()
        getViewDataBinding().root.snackAlert(errorMess)
    }

    protected val viewModelRegistration = ArrayList<BasePresenterViewModel>()

}