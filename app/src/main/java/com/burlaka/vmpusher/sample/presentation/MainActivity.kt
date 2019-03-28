package com.burlaka.vmpusher.sample.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.burlaka.utils.ext.setContentFragment
import com.burlaka.vmpusher.sample.BR
import com.burlaka.vmpusher.sample.R
import com.burlaka.vmpusher.sample.databinding.MainActivityBinding
import com.burlaka.vmpusher.sample.presentation.base.BaseActivity
import com.burlaka.vmpusher.sample.viewmodel.MainViewModel
import com.burlaka.vmpusher.sample.viewmodel.MyViewModelFactory
import com.jellyworkz.processor.Navigator.startActionViaNavigator


class MainActivity : BaseActivity<MainActivityBinding>(),
    MainViewModel.Companion.Navigator {

    override fun layoutId(): Int = R.layout.main_activity
    private lateinit var mMainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewDataBinding()

        this@MainActivity receiveMessagesFrom mMainViewModel

        this@MainActivity receivePushesFrom mMainViewModel
    }

    override fun getNavigate() = { id: Int ->
        startActionViaNavigator(listenerId = this, actionId = id)
    }

    override fun showSecureScreen() {
        setContentFragment(containerViewId = contentId) {
            SecureFragment.newInstance()
        }
    }

    override fun getBaseViewModel() =
        ViewModelProviders.of(this, MyViewModelFactory(application))[MainViewModel::class.java].apply {
            mMainViewModel = this
        }

    private infix fun AppCompatActivity.receiveMessagesFrom(viewModel: MainViewModel): MainViewModel {
        viewModel.messages.observe(this@MainActivity, Observer { singleEvent ->
            singleEvent.getContentIfNotHandled()?.let {
                when (it.isFailed) {
                    true -> {
                        showFailedMess(it.message)
                    }
                    else -> {
                        showMessage(it.message)
                    }
                }
            }
        })
        return viewModel
    }

    override fun bindingVmVariable(): Int = BR.viewModel
    private lateinit var mBinding: MainActivityBinding
    private val contentId = R.id.fl_content
}