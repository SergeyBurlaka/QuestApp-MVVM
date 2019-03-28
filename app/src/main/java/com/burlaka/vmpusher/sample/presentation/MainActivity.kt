package com.burlaka.vmpusher.sample.presentation

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.burlaka.utils.ext.setContentFragment
import com.burlaka.vmpusher.sample.BR
import com.burlaka.vmpusher.sample.R
import com.burlaka.vmpusher.sample.databinding.MainActivityBinding
import com.burlaka.vmpusher.sample.presentation.base.BaseActivity
import com.burlaka.vmpusher.sample.viewmodel.MainPresenterViewModel
import com.burlaka.vmpusher.sample.viewmodel.MyViewModelFactory
import com.jellyworkz.processor.MainView.performTaskForMainView


class MainActivity : BaseActivity<MainActivityBinding>(),
    MainPresenterViewModel.Companion.MainView {


    override fun layoutId(): Int = R.layout.main_activity
    private lateinit var mMainPresenterViewModel: MainPresenterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewDataBinding()

        this@MainActivity receiveTaskFrom mMainPresenterViewModel
    }

    override val vmPushExcutable = { id: Int ->
        performTaskForMainView(listenerId = this, actionId = id)
    }

    override fun showSecureScreen() {
        setContentFragment(contentId, true) {
            SecureFragment.newInstance()
        }
    }

    override fun showProgressBar() {
        //
    }

    override fun upgradeToolbar() {
        //
    }

    override fun changeColor() {
        //
    }

    override fun showClock() {
        //
    }

    override fun getBaseViewModel() =
        ViewModelProviders.of(this, MyViewModelFactory(application))[MainPresenterViewModel::class.java].apply {
            mMainPresenterViewModel = this
        }

    override fun bindingVmVariable(): Int = BR.viewModel
    private lateinit var mBinding: MainActivityBinding
    private val contentId = R.id.fl_content
}