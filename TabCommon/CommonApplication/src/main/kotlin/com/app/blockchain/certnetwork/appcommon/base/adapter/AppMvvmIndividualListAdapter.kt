package com.app.blockchain.certnetwork.appcommon.base.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.app.blockchain.certnetwork.common.base.mvvm.layer1View.BaseMvvmIndividualListAdapter
import com.app.blockchain.certnetwork.common.base.mvvm.layer2ViewModel.BaseListAdapterViewModel
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseViewHolder

/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */
abstract class AppMvvmIndividualListAdapter<
        VH : BaseViewHolder<*>,
        VM : BaseListAdapterViewModel>
    : BaseMvvmIndividualListAdapter<VH, VM> {

    constructor(activity: FragmentActivity) : super(activity) {}

    constructor(fragment: Fragment) : super(fragment) {}


}