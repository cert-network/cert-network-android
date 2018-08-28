package com.app.blockchain.certnetwork.appcommon.base.mvvm

import android.os.Build
import android.os.Bundle
import com.app.blockchain.certnetwork.appcommon.base.mvvm.error.handling.ErrorHandlingMvvmSlidingActivity


/**
* Created by「 The Khaeng 」on 27 Aug 2017 :)
*/

abstract class AppMvvmSlidingActivity
    : ErrorHandlingMvvmSlidingActivity() {

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

//    override
//    fun setupMenu(menu: SlidingMenu) {
//        super.setupMenu(menu)
//        menu.setBehindWidth(resources.getDimension(R.dimen.drawer_navigation_size).toInt())
//        menu.touchmodeMarginThreshold = resources.getDimension(R.dimen.margin_drawer_touch).toInt()
//        menu.touchModeAbove = SlidingMenu.TOUCHMODE_MARGIN
//    }

    override
    fun setOverridePendingStartTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && !isInMultiWindowMode) {
            super.setOverridePendingStartTransition()
        }
    }

    override
    fun setOverridePendingEndTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && !isInMultiWindowMode) {
            super.setOverridePendingEndTransition()
        }
    }
}