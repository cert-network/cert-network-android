package com.app.blockchain.certnetwork.appcommon.widgets.toolbar

import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import com.app.blockchain.certnetwork.appcommon.R
import com.app.blockchain.certnetwork.common.extension.view.hide
import com.app.blockchain.certnetwork.common.extension.view.show


/**
 * Created by「 The Khaeng 」on 16 Oct 2017 :)
 */
@Suppress("UNUSED_PARAMETER")
class AppToolbar
@JvmOverloads
constructor(context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = R.attr.toolbarStyle)
    : Toolbar(context, attrs, defStyleAttr) {

    private var title: String = ""
    private var titleColor: Int = 0
    private var backColor: Int = 0
    private var isShowShadow: Boolean = true
    private var tvTitle: TextView? = null
    private var btnBack: ImageView? = null
    private var icPrimary: ImageView? = null
    private var icSecondary: ImageView? = null
    private var divider: View? = null
    private var icPrimaryRes: Int = 0
    private var icSecondaryRes: Int = 0

    init {
        initWithAttrs(attrs, defStyleAttr, 0)
        setInset()
        setup()
    }



    private fun initWithAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        var attrArray = context.obtainStyledAttributes(attrs,
                intArrayOf(android.R.attr.title))
        title = attrArray.getText(0)?.toString() ?: ""
        attrArray.recycle()
        attrArray = context.obtainStyledAttributes(attrs, R.styleable.AppToolbar, defStyleRes, 0)
        backColor = attrArray.getColor(R.styleable.AppToolbar_toolbarBackColor, 0)
        titleColor = attrArray.getColor(R.styleable.AppToolbar_toolbarTitleColor, 0)
        icPrimaryRes = attrArray.getResourceId(R.styleable.AppToolbar_toolbarPrimaryIcon, 0)
        icSecondaryRes = attrArray.getResourceId(R.styleable.AppToolbar_toolbarSecondaryIcon, 0)
        isShowShadow = attrArray.getBoolean(R.styleable.AppToolbar_toolbarShowShadow, true)
        attrArray.recycle()

    }

    private fun setup() {
        val view = View.inflate(context, R.layout.toolbar_simple, this)
        bindView(view)
        setupView()
    }


    private fun bindView(view: View) {
        tvTitle = view.findViewById(R.id.toolbar_tv_title)
        btnBack = view.findViewById(R.id.toolbar_btn_back)
        icPrimary = view.findViewById(R.id.toolbar_ic_primary)
        icSecondary = view.findViewById(R.id.toolbar_ic_secondary)
        divider = view.findViewById(R.id.holder_contact_item_divider)
    }


    private fun setupView() {
        setTitle(title)
        if (titleColor != 0) {
            tvTitle?.setTextColor(titleColor)
        }

        if (backColor != 0) {
            btnBack?.setColorFilter(backColor, PorterDuff.Mode.SRC_IN)
        }

        if (icPrimaryRes != 0) {
            icPrimary.show()
            icPrimary?.setImageResource(icPrimaryRes)
        } else {
            icPrimary.hide()
        }

        if (icSecondaryRes != 0) {
            icSecondary.show()
            icSecondary?.setImageResource(icSecondaryRes)
        } else {
            icSecondary.hide()
        }

        divider.show(isShowShadow)
    }

    private fun setInset() {
        setContentInsetsAbsolute(0, 0)
        setContentInsetsRelative(0, 0)
        //require: for tablet
        setPadding(
                0, // left
                0, // top
                0, // right
                0) // bottom
    }

    fun setTitle(title: String?) {
        tvTitle?.text = title
    }

    override
    fun setTitle(@StringRes title: Int) {
        tvTitle?.text = context.getString(title)
    }

    fun registerWithBackButton(fragmentActivity: FragmentActivity) {
        btnBack?.setOnClickListener { fragmentActivity.onBackPressed() }
    }

    fun setOnClickPrimaryIconListener(listener: View.OnClickListener?) {
        icPrimary?.setOnClickListener(listener)
    }


    fun setOnClickSecondaryIconListener(listener: View.OnClickListener?) {
        icSecondary?.setOnClickListener(listener)
    }

    fun registerWithFinishButton(fragmentActivity: FragmentActivity) {
        btnBack?.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fragmentActivity.finishAfterTransition()
            } else {
                fragmentActivity.finish()
            }
        }
    }

    fun registerWithBackButton(fragment: Fragment) {
        btnBack?.setOnClickListener { fragment.activity?.onBackPressed() }
    }


}
