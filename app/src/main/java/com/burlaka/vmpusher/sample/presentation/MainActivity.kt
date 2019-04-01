package com.burlaka.vmpusher.sample.presentation

import android.graphics.drawable.Animatable
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
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
    private val animatedVectorDrawableCompat by lazy {
        AnimatedVectorDrawableCompat.create(
            this,
            R.drawable.anim_svg_timer
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewDataBinding()
        mBinding.lifecycleOwner = this
        this@MainActivity receiveTaskFrom mMainPresenterViewModel
    }

    override val vmPushExcutable = { id: Int ->
        performTaskForMainView(listenerId = this, actionId = id)
    }

    override fun startClock() {
        mBinding.ivProgress.apply {
            setImageDrawable(animatedVectorDrawableCompat)
            (drawable as Animatable).apply {
                if(this.isRunning){
                    stop()
                }
                start()
            }
        }
    }

    override fun getBaseViewModel() =
        ViewModelProviders.of(
            this@MainActivity,
            MyViewModelFactory(application)
        )[MainPresenterViewModel::class.java].apply {
            mMainPresenterViewModel = this
        }
    override fun bindingVmVariable(): Int = BR.viewModel
    private lateinit var mBinding: MainActivityBinding
    private val contentId = R.id.fl_content
}