package com.burlaka.vmpusher.sample.presentation.base

import android.graphics.Color
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import com.burlaka.utils.ext.hideKeyboardExt
import com.burlaka.utils.ext.log_d
import com.burlaka.utils.ext.showKeyboardExt
import com.burlaka.utils.ext.snackAlert
import com.burlaka.vmpusher.INavigate
import com.burlaka.vmpusher.BasePushViewModel


abstract class BaseActivity<T : ViewDataBinding> :
    AppCompatActivity(),
    LifecycleObserver,
    FragmentHost,
    INavigate {

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
    private var baseViewModel: BasePushViewModel? = null

    /**
     * Base VM activityBind
     * @return base VM if exist
     */
    abstract fun getBaseViewModel(): BasePushViewModel?

    /**
     * Override for set binding variable
     * @return variable id
     */
    abstract fun bindingVmVariable(): Int?

    private fun BasePushViewModel.dataBind(): BasePushViewModel {
        viewDataBinding?.let {
            it.setVariable(bindingVmVariable()!!, this)
            it.executePendingBindings()
        }
        return this
    }

    private fun BasePushViewModel.activityBind(): BasePushViewModel {
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

    protected val viewModelRegistration = ArrayList<BasePushViewModel>()

    init {
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun create() {

                viewModelRegistration.forEach { viewModel ->
                    log_d(" activity:on create $viewModel")

                    viewModel.create()
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun destroyView() {
                viewModelRegistration.forEach { viewModel ->
                    viewModel.detachView()
                }
            }
        })
    }
}