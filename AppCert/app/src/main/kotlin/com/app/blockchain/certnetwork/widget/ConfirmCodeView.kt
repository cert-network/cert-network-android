package com.app.blockchain.certnetwork.widget

import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import android.support.annotation.RequiresApi
import android.text.TextWatcher
import android.util.AttributeSet
import com.app.blockchain.certnetwork.R
import com.app.blockchain.certnetwork.common.base.view.BaseViewGroup
import com.app.blockchain.certnetwork.common.extension.view.invisible
import com.app.blockchain.certnetwork.common.extension.view.show
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.widget_confirm_code.view.confirm_code_pin as pin
import kotlinx.android.synthetic.main.widget_confirm_code.view.confirm_code_tv_count_down as tvCountDownTime
import kotlinx.android.synthetic.main.widget_confirm_code.view.confirm_code_tv_error as tvError


/**
 * Created by TheKhaeng
 */

class ConfirmCodeView : BaseViewGroup {

    companion object {
        const val CODE_LENGTH = 6
        const val VALIDATE = 1
        const val INVALIDATE = 2
    }

    @JvmOverloads
    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int,
            defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    var code: String
        get() = pin.text.toString().trim()
        set(value) {
            pin.setText(value)
        }

    private var countDown: CountDownTimer? = null

    override
    fun getLayoutRes(): Int = R.layout.widget_confirm_code

    private var countDownTimeSecond: Long = 0

    override
    fun setupStyleables(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {
        val attrArray = context.obtainStyledAttributes(attrs, R.styleable.ConfirmCodeView, defStyleRes, 0)
        countDownTimeSecond = attrArray.getInt(R.styleable.ConfirmCodeView_countDownTimeSecond, 0).toLong()
        attrArray.recycle()
    }


    override
    fun setupView() {
        startCountDown()
    }

    fun startCountDown(second: Long = countDownTimeSecond, isFinish: (isFinish: Boolean) -> Unit = {}) {
        var isStarted = false
        countDown?.cancel()
        countDown = object : CountDownTimer(second * 1000L, 1000) {

            override
            fun onTick(millisUntilFinished: Long) {
                if (!isStarted) {
                    isFinish.invoke(false)
                    isStarted = true
                }
                tvCountDownTime.text =
                        String.format("%02d:%02d",
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
            }

            override
            fun onFinish() {
                isFinish.invoke(true)
                tvCountDownTime.text = "00:00"
            }
        }.start()
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        pin.addTextChangedListener(watcher)
    }

    fun setError(message: String) {
        if (message.isEmpty()) {
            tvError.invisible()
        } else {
            tvError.show()
            tvError.text = message
        }
    }

    fun validateCode(): Int {
        if (code.isEmpty()) {
            tvError.show()
            tvError.text = context.getString(R.string.alert_error_confirm_code_empty)
            return INVALIDATE
        } else if (code.length != CODE_LENGTH) {
            tvError.show()
            tvError.text = context.getString(R.string.alert_error_confirm_code_length)
            return INVALIDATE
        }
        tvError.text = ""
        tvError.invisible()
        return VALIDATE
    }


}
