package com.app.blockchain.certnetwork.appcommon.widgets

import android.content.Context
import android.content.res.ColorStateList
import android.support.design.button.MaterialButton
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import com.app.blockchain.certnetwork.appcommon.R
import com.app.blockchain.certnetwork.common.extension.getColorFromId
import com.thekhaeng.pushdownanim.PushDownAnim


@Suppress("UNUSED_PARAMETER")
class AppMaterialButton
@JvmOverloads
constructor(context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = R.attr.materialButtonStyle)
    : MaterialButton(context, attrs, defStyleAttr) {

    companion object {
        private val CLICK_DURATION = 100L
    }

    private var pushDownScale: Float = 0.0f
    private var enablePushDown: Boolean = true
    private var backupTextColor: Int = 0
    private var backupBackgroundTintList: ColorStateList? = null


    init {
        initWithAttrs(attrs, defStyleAttr, 0)
        init(context, attrs)
    }


    private fun initWithAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val attrArray = context.obtainStyledAttributes(attrs, R.styleable.AppButtonView, defStyleRes, 0)
        pushDownScale = attrArray.getFloat(R.styleable.AppButtonView_pushDownScale, 0.97f)
        enablePushDown = attrArray.getBoolean(R.styleable.AppButtonView_enablePushDown, true)
        attrArray.recycle()

        backupBackgroundTintList = backgroundTintList
        backupTextColor = currentTextColor
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        if (enablePushDown) {
            PushDownAnim.setPushDownAnimTo(this)
                    .setScale(PushDownAnim.MODE_STATIC_DP, 2f)
        }
    }

    override
    fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        backgroundTintList =
                if (enabled) {
                    setTextColor(backupTextColor)
                    backupBackgroundTintList
                } else {
                    setTextColor(context.getColorFromId(R.color.text_active_white))
                    ContextCompat.getColorStateList(context, R.color.button_grey_light)
                }
    }

}