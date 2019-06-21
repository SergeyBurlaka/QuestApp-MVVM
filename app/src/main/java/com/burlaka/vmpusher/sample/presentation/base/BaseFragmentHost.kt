package com.burlaka.vmpusher.sample.presentation.base


interface FragmentHost : Callback {
    fun hideKeyboard()
    fun returnBack()
    fun showKeyboard()
}

interface Callback {

    fun onFragmentAttached()

    fun onFragmentDetached(tag: String)
}
