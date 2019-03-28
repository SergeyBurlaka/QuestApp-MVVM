package com.burlaka.vmpusher.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.burlaka.utils.ext.replaceFragment


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)
        loginViewModel = ViewModelProviders.of(this)[LoginViewModel::class.java]
        replaceFragment(EmailFragment.newInstance(), R.id.holder, false)
    }

    private val TAG by lazy { javaClass.simpleName }
}