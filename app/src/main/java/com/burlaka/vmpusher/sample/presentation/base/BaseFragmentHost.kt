package com.burlaka.vmpusher.sample.presentation.base

/**
 * @author Sergey
 * @since 06.02.2019
 */
interface FragmentHost : Callback {
    fun hideKeyboard()
    fun returnBack()
    fun showKeyboard()
}

interface Callback {

    fun onFragmentAttached()

    fun onFragmentDetached(tag: String)
}
