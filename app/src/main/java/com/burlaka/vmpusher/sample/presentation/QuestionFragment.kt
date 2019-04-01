package com.burlaka.vmpusher.sample.presentation

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.burlaka.vmpusher.sample.R
import com.burlaka.vmpusher.sample.databinding.HomeFragmentBinding
import com.burlaka.vmpusher.sample.presentation.base.BaseFragment
import com.burlaka.vmpusher.sample.viewmodel.MainPresenterViewModel


class QuestionFragment : BaseFragment<HomeFragmentBinding>() {

    override val layoutId: Int = R.layout.home_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ViewModelProviders.of(activity!!)[MainPresenterViewModel::class.java].bindToView()

    }

    private fun MainPresenterViewModel.bindToView(): MainPresenterViewModel {
        viewDataBinding.viewModel = this
        return this
    }
}