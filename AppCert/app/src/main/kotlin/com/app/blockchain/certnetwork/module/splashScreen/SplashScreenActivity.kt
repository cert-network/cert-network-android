package com.app.blockchain.certnetwork.module.splashScreen

import android.os.Bundle
import com.app.blockchain.certnetwork.R
import com.app.blockchain.certnetwork.appcommon.base.mvvm.AppMvvmActivity
import com.app.blockchain.certnetwork.common.extension.delay
import com.app.blockchain.certnetwork.module.create.account.CreateAccountActivity
import com.app.blockchain.certnetwork.module.main.MainActivity

/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

class SplashScreenActivity : AppMvvmActivity() {

    private lateinit var viewModel: SplashScreenViewModel

    companion object {
        private const val DELAY: Long = 4000
    }

    override
    fun isGoToLockscreen(): Boolean = false


    override
    fun onSetupViewModel(savedInstanceState: Bundle?) {
        super.onSetupViewModel(savedInstanceState)
        viewModel = getViewModel(SplashScreenViewModel::class.java)

    }

    override
    fun setupLayoutView(): Int = NO_LAYOUT

    override
    fun setOverridePendingStartTransition() {
        super.setOverridePendingStartTransition()
        this.overridePendingTransition(
                R.anim.activity_open_enter,
                R.anim.activity_close_exit)
    }

    override
    fun setOverridePendingEndTransition() {
        super.setOverridePendingEndTransition()
        this.overridePendingTransition(
                R.anim.activity_open_enter,
                R.anim.activity_close_exit)
    }


    override
    fun onSetupView(savedInstanceState: Bundle?) {
        super.onSetupView(savedInstanceState)
        delay({
            if (viewModel.loadAccount() == null) {
                openActivityWithFinish(CreateAccountActivity::class.java)
            }else{
                openActivityWithFinish(MainActivity::class.java)
            }
        }, DELAY)
    }

}

