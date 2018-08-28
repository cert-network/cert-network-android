package com.app.blockchain.certnetwork.common.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout

/**
 * Created by「 The Khaeng 」on 18 Aug 2018 :)
 */
class FragmentFrameLayout : FrameLayout {

    private var windowInsets: WindowInsets? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)


    override
    fun onApplyWindowInsets(windowInsets: WindowInsets): WindowInsets {
        this.windowInsets = windowInsets
        // propagates window insets to children's
        for (index in 0 until childCount) {
            getChildAt(index).dispatchApplyWindowInsets(windowInsets)
        }
        return windowInsets
    }

    override
    fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        for (index in 0 until childCount) {
            this.windowInsets?.let {
                getChildAt(index).dispatchApplyWindowInsets(it)
            }
        }
    }
}
