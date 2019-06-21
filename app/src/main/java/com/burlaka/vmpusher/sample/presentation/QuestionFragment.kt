package com.burlaka.vmpusher.sample.presentation

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.burlaka.vmpusher.sample.R
import com.burlaka.vmpusher.sample.databinding.QuestionFragmentBinding
import com.burlaka.vmpusher.sample.presentation.base.BaseFragment
import com.burlaka.vmpusher.sample.viewmodel.MainViewModel


class QuestionFragment(override val layoutId: Int = R.layout.question_fragment) :
    BaseFragment<QuestionFragmentBinding>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ViewModelProviders.of(activity!!)[MainViewModel::class.java].bindToView()
        mViewDataBinding.lifecycleOwner = this
    }

    private fun MainViewModel.bindToView(): MainViewModel {
        mViewDataBinding.viewModel = this
        return this
    }

    companion object {
        fun newInstance() = QuestionFragment()
    }
}