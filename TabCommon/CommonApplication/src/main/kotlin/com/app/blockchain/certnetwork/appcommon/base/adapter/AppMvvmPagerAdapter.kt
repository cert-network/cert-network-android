package com.app.blockchain.certnetwork.appcommon.base.adapter


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.app.blockchain.certnetwork.common.base.mvvm.layer1View.BaseMvvmPagerAdapter


/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

abstract class AppMvvmPagerAdapter
    : BaseMvvmPagerAdapter {



    constructor(activity: FragmentActivity) : super(activity) {
    }

    constructor(fragment: Fragment) : super(fragment) {}

}

