package com.app.blockchain.certnetwork.common.extension.view

import android.support.annotation.DimenRes
import android.support.v4.app.Fragment
import com.app.blockchain.certnetwork.common.extension.getFloatDimen

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

fun Fragment.getFloatDimen(@DimenRes resId: Int): Float? {
    return this.context?.getFloatDimen(resId)
}

