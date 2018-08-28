package com.app.blockchain.certnetwork.common.base.view

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View

/**
 * Created by「 The Khaeng 」on 20 Feb 2018 :)
 */

@Suppress("UNUSED_PARAMETER")
abstract class BaseView : View {

    @JvmOverloads
    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr) {
        setup(attrs, defStyleAttr, 0)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        setup(attrs, defStyleAttr, defStyleRes)
    }


    private fun setup(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs != null) setupStyleables(attrs, defStyleAttr, defStyleRes)
        setupInstance()
        setupView()
    }

    protected fun setupStyleables(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {}


    protected fun setupInstance() {}

    protected abstract fun setupView()

}
