package com.app.blockchain.certnetwork.common.base.view

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View

/**
 * Created by「 The Khaeng 」on 20 Feb 2018 :)
 */

@Suppress("UNUSED_PARAMETER")
abstract class ViewSavedState : View.BaseSavedState {
    @get:JvmName("getSuperState_")
    var superState: Parcelable = Bundle()

    constructor(superState: Parcelable) : super(EMPTY_STATE) {
        this.superState = superState
    }

    constructor(`in`: Parcel) : super(`in`) {
        this.superState = `in`.readParcelable(javaClass.classLoader)
    }

    override
    fun writeToParcel(out: Parcel, flags: Int) = with(out) {
        super.writeToParcel(out, flags)
        out.writeParcelable(superState, flags)
    }

}
