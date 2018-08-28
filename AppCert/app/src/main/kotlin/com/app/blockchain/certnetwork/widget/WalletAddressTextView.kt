package com.app.blockchain.certnetwork.widget

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Typeface.BOLD
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.AppCompatTextView
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import com.app.blockchain.certnetwork.R
import com.app.blockchain.certnetwork.common.base.view.ViewSavedState
import com.app.blockchain.certnetwork.common.extension.getAttrColor
import com.app.blockchain.certnetwork.common.extension.toast
import com.app.blockchain.certnetwork.common.extension.view.setPaddingBottom
import com.app.blockchain.certnetwork.common.extension.view.setPaddingTop
import com.thekhaeng.materialdesign.metric.dpToPxInt


@Suppress("UNUSED_PARAMETER")
class WalletAddressTextView
@JvmOverloads
constructor(context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = android.R.attr.textViewStyle)
    : AppCompatTextView(context, attrs, defStyleAttr){

    private var textSecondaryColor: Int = 0
    private val UPPERCASE_LENGTH: Int = 5

    var address: String = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                text = value
            }
        }

    var aliasName: String = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                text = value
            }
        }


    init {
        setOnLongClickListener { copyAddress() }
        setPaddingTop(dpToPxInt(2f))
        setPaddingBottom(dpToPxInt(2f))
        initWithAttrs(attrs, defStyleAttr, 0)
    }


    private fun initWithAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val attrArray = context.obtainStyledAttributes(attrs, R.styleable.WalletAddressTextView, defStyleRes, 0)
        textSecondaryColor = attrArray.getColor(R.styleable.WalletAddressTextView_textSecondaryColor, 0)
        attrArray.recycle()
    }


    @Deprecated("use set address", ReplaceWith("address"))
    override
    fun setText(text: CharSequence, type: BufferType) {
        val span = if (isHexAddress(text)) {
            getBoldTextRangeSpan(text, 2)
        } else {
            getBoldTextRangeSpan(text, 0)
        }
        super.setText(span, type)
    }

    override
    fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.address = this.address
        ss.aliasName = this.aliasName
        return ss
    }

    override
    fun onRestoreInstanceState(state: Parcelable) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        this.address = state.address
        this.aliasName = state.aliasName
    }

    fun copyAddress(): Boolean {
        val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Wallet Address", address.replace("\\s".toRegex(), ""))
        clipboard.primaryClip = clip
        context.toast("Copy Wallet Address.")
        return true
    }

    private fun getBoldTextRangeSpan(charSequence: CharSequence, start: Int = 0): SpannableString {

        val newCharSequence =
                if (charSequence.length > UPPERCASE_LENGTH) {
                    if (start > 0) {
                        charSequence.subSequence(0, start).toString() +
                                " " + charSequence.subSequence(start, start + UPPERCASE_LENGTH).toString() +
                                " " + charSequence.subSequence(start + UPPERCASE_LENGTH, charSequence.length)
                    } else {
                        charSequence.subSequence(0, UPPERCASE_LENGTH).toString() + " " + charSequence.subSequence(UPPERCASE_LENGTH, charSequence.length)
                    }
                } else {
                    charSequence
                }

        return SpannableString(newCharSequence).apply {
            if (charSequence.length > UPPERCASE_LENGTH) {
                val tmpColors = textColors.defaultColor
                if (textSecondaryColor != 0) {
                    setSpan(ForegroundColorSpan(textSecondaryColor), 0, newCharSequence.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                } else {
                    setSpan(ForegroundColorSpan(context.getAttrColor(R.attr.colorTextInactive)), 0, newCharSequence.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }

                val newStart =
                        if (start != 0) {
                            start + 1
                        } else {
                            start
                        }
                setSpan(StyleSpan(BOLD), newStart, newStart + UPPERCASE_LENGTH, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(ForegroundColorSpan(tmpColors), newStart, newStart + UPPERCASE_LENGTH, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(RelativeSizeSpan(1.05f), newStart, newStart + UPPERCASE_LENGTH, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)


                newCharSequence.forEachIndexed { index, char ->
                    if (char == ' ') {
                        setSpan(RelativeSizeSpan(0.2f), index, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                }

            } else {
                setSpan(StyleSpan(BOLD), 0, charSequence.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(RelativeSizeSpan(1.05f), 0, charSequence.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    private fun isHexAddress(address: CharSequence): Boolean {
        if (address.length > 2) {
            return address[0].toLowerCase() == '0' && address[1].toLowerCase() == 'x'
        }
        return false
    }


    override
    fun getText(): CharSequence {
        return super.getText().replace("\\s+".toRegex(), " ")
    }

    open class SavedState : ViewSavedState {
        var address: String = ""
        var aliasName: String = ""

        internal constructor(superState: Parcelable) : super(superState)

        internal constructor(`in`: Parcel) : super(`in`) {
            this.address = `in`.readString() ?: ""
            this.aliasName = `in`.readString() ?: ""
        }

        override
        fun writeToParcel(out: Parcel, flags: Int) = with(out) {
            super.writeToParcel(out, flags)
            out.writeString(address)
            out.writeString(aliasName)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {

                override
                fun createFromParcel(`in`: Parcel): SavedState? = SavedState(`in`)

                override
                fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
            }
        }
    }


}