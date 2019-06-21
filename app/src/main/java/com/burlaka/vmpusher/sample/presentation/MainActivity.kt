package com.burlaka.vmpusher.sample.presentation

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.burlaka.utils.ext.setContentFragment
import com.burlaka.vmpusher.sample.BR
import com.burlaka.vmpusher.sample.R
import com.burlaka.vmpusher.sample.databinding.MainActivityBinding
import com.burlaka.vmpusher.sample.presentation.base.BaseActivity
import com.burlaka.vmpusher.sample.viewmodel.MainViewModel
import com.burlaka.vmpusher.sample.viewmodel.MyViewModelFactory
import com.jellyworkz.processor.MainView.performTaskForMainView


class MainActivity : BaseActivity<MainActivityBinding>(), MainViewModel.MainView {

    override fun layoutId(): Int = R.layout.main_activity
    private lateinit var mMainViewModel: MainViewModel

    override fun startClock() {
        mBinding.apply {
            vTimer.visibility = View.VISIBLE
            ivStartScreen.visibility = View.GONE
            ivProgress.apply {
                setImageDrawable(animatedVectorDrawableCompat)
                (drawable as Animatable).apply {
                    if (this.isRunning) {
                        stop()
                    }
                    start()
                }
            }
        }
    }

    override fun showFirstQuestion() {
        setContentFragment(containerViewId = contentId, backStack = true) { QuestionFragment.newInstance() }
    }

    override fun showFinishTest() {
        mBinding.vTimer.visibility = View.GONE
        setContentFragment(containerViewId = contentId, backStack = true) { SuccessFragment.newInstance() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewDataBinding()
        mBinding.lifecycleOwner = this
        this@MainActivity receiveTaskFrom mMainViewModel
    }

    override val vmPushExcutable = { actionId: Int ->
        performTaskForMainView(this, actionId)
    }

    override fun getBaseViewModel() =
        ViewModelProviders.of(
            this@MainActivity,
            MyViewModelFactory(application)
        )[MainViewModel::class.java].apply {
            mMainViewModel = this
        }

    override fun bindingVmVariable(): Int = BR.viewModel
    private lateinit var mBinding: MainActivityBinding
    private val contentId = R.id.fl_content
    private val animatedVectorDrawableCompat by lazy {
        AnimatedVectorDrawableCompat.create(
            this,
            R.drawable.anim_svg_timer
        )
    }

}