package com.app.blockchain.certnetwork.appcommon.base.mvvm

import android.os.Build
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED
import android.support.design.widget.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import android.support.design.widget.CollapsingToolbarLayout
import com.app.blockchain.certnetwork.appcommon.base.mvvm.error.handling.ErrorHandlingMvvmActivity
import com.app.blockchain.certnetwork.common.extension.view.setScrollFlags


/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

abstract class AppMvvmActivity
    : ErrorHandlingMvvmActivity() {



    open fun isGoToLockscreen(): Boolean = true

    override
    fun onResume() {
        super.onResume()
    }


    override
    fun onPause() {
        super.onPause()
    }

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


    fun enableAppbarScrolling(toolbar: CollapsingToolbarLayout,
                              enabled: Boolean) {
        if (enabled) {
            toolbar.setScrollFlags(SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED)
        } else {
            toolbar.setScrollFlags(0)
        }
    }


}

