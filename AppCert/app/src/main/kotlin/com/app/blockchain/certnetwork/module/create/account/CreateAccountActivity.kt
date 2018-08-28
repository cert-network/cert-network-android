package com.app.blockchain.certnetwork.module.create.account

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import com.app.blockchain.certnetwork.R
import com.app.blockchain.certnetwork.appcommon.base.mvvm.AppMvvmActivity
import com.app.blockchain.certnetwork.common.extension.view.doubleBackPressedFinish
import com.app.blockchain.certnetwork.module.create.account.fragment.PutInformationFragment


/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */

class CreateAccountActivity : AppMvvmActivity() {

    private lateinit var viewModel: CreateAccountViewModel

    override
    fun isGoToLockscreen(): Boolean = false

    override
    fun onPreCreate(savedInstanceState: Bundle?) {
        super.onPreCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override
    fun onSetupViewModel(savedInstanceState: Bundle?) {
        super.onSetupViewModel(savedInstanceState)
        viewModel = getViewModel(CreateAccountViewModel::class.java).apply {
        }
    }

    override
    fun setupLayoutView(): Int = R.layout.activity_create_account

    override
    fun onBindView(savedInstanceState: Bundle?) {
        super.onBindView(savedInstanceState)
        bindFragmentContainer(R.id.create_account_fragment_container)
    }

    override
    fun onInitialize(savedInstanceState: Bundle?) {
        super.onInitialize(savedInstanceState)
        initFragment(PutInformationFragment.newInstance(), savedInstanceState)
    }

    override
    fun onBackPressed() {
        if (getCurrentFragment() is PutInformationFragment) {
            doubleBackPressedFinish()
        } else {
            super.onBackPressed()
        }
    }



}

