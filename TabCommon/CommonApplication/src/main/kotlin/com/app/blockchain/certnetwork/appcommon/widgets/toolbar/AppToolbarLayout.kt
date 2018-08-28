package com.app.blockchain.certnetwork.appcommon.widgets.toolbar

import android.content.Context
import android.support.v7.appcompat.R
import android.support.v7.widget.Toolbar
import android.util.AttributeSet


/**
 * Created by「 The Khaeng 」on 14 Feb 2018 :)
 */

class AppToolbarLayout
@JvmOverloads
constructor(context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = R.attr.toolbarStyle)
    : Toolbar(context, attrs, defStyleAttr) {

    init {
        setup(context)
    }

    fun setup(context: Context) {
        setupView()
    }


    fun setupView() {
        setInset()
    }

    private fun setInset() {
        setContentInsetsAbsolute(0, 0)
        setContentInsetsRelative(0, 0)
        setPadding(0, 0, 0, 0) //require: for tablet
    }
}
