package com.burlaka.vmpusher.sample.presentation

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.burlaka.vmpusher.sample.R
import com.burlaka.vmpusher.sample.databinding.HomeFragmentBinding
import com.burlaka.vmpusher.sample.presentation.base.BaseFragment
import com.burlaka.vmpusher.sample.viewmodel.MainViewModel


class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    override val layoutId: Int = R.layout.home_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ViewModelProviders.of(activity!!)[MainViewModel::class.java].bindToView()

    }

    private fun MainViewModel.bindToView(): MainViewModel {
        viewDataBinding.viewModel = this
        return this
    }
}