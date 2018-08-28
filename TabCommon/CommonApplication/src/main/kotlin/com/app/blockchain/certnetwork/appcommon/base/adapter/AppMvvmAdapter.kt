package com.app.blockchain.certnetwork.appcommon.base.adapter


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.app.blockchain.certnetwork.common.base.mvvm.layer1View.BaseMvvmListAdapter
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseViewHolder


/**
 * Created by「 The Khaeng 」on 19 Sep 2017 :)
 */

abstract class AppMvvmAdapter<
        VH : BaseViewHolder<*>>
    : BaseMvvmListAdapter<VH> {

    constructor(activity: FragmentActivity) : super(activity) {}

    constructor(fragment: Fragment) : super(fragment) {}


}

