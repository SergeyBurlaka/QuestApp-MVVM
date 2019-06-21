package com.burlaka.vmpusher.sample.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.burlaka.vmpusher.sample.R
import com.burlaka.vmpusher.sample.databinding.SuccessFragmentBinding
import com.burlaka.vmpusher.sample.presentation.base.BaseFragment
import com.burlaka.vmpusher.sample.viewmodel.MainViewModel
import com.burlaka.vmpusher.sample.viewmodel.SecureViewModel


class SuccessFragment(override val layoutId: Int = R.layout.success_fragment) : BaseFragment<SuccessFragmentBinding>() {

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
        fun newInstance() = SuccessFragment()
    }
}
