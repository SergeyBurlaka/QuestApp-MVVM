package com.burlaka.vmpusher.sample.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.burlaka.vmpusher.sample.R
import com.burlaka.vmpusher.sample.viewmodel.SecureViewModel


class FailedFragment : Fragment() {

    companion object {
        fun newInstance() = FailedFragment()
    }

    private lateinit var viewModel: SecureViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.success_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SecureViewModel::class.java)

    }

}
