package com.app.blockchain.certnetwork.appcommon.base.adapter


import android.content.Context
import com.app.blockchain.certnetwork.common.base.mvvm.layer1View.BaseListAdapter
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseViewHolder


/**
 * Created by「 The Khaeng 」on 19 Sep 2017 :)
 */

abstract class AppAdapter<
        VH : BaseViewHolder<*>>(
        context: Context?
) : BaseListAdapter<VH>(context) {

}

