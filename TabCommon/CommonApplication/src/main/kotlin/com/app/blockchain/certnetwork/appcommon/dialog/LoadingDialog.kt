package com.app.blockchain.certnetwork.appcommon.dialog

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.app.DialogFragment
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.app.blockchain.certnetwork.appcommon.R


/**
 * Created by TheKhaeng.
 */
@Suppress("UNUSED_PARAMETER")
open class LoadingDialog : DialogFragment() {

    companion object {

        const val KEY_MESSAGE = "LoadingDialog:key_message"
        const val KEY_ICON = "LoadingDialog:key_icon"
        const val KEY_LAYOUT = "LoadingDialog:key_layout"

        private fun newInstance(
                message: String,
                icon: Int,
                layout: Int = R.layout.dialog_loading,
                isCancelable: Boolean = false): LoadingDialog {
            val fragment = LoadingDialog()
            val bundle = Bundle().apply {
                putString(KEY_MESSAGE, message)
                putInt(KEY_LAYOUT, layout)
                putInt(KEY_ICON, icon)
            }
            fragment.isCancelable = isCancelable
            fragment.arguments = bundle
            return fragment
        }
    }

    val message: String get() = arguments?.getString(KEY_MESSAGE) ?: ""
    val icon: Int get() = arguments?.getInt(KEY_ICON) ?: 0
    val layout: Int get() = arguments?.getInt(KEY_LAYOUT) ?: 0

    private lateinit var tvMessage: AppCompatTextView
    private lateinit var imgIcon: AppCompatImageView


    override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        return inflater.inflate(layout, container)
    }


    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
        setupView()
    }

    private fun bindView(view: View?) {
        view?.let {
            tvMessage = view.findViewById(R.id.loading_dialog_tv_message)
            imgIcon = view.findViewById(R.id.loading_dialog_icon)
        }
    }

    private fun setupView() {
        tvMessage.text = message
        if (icon != 0) {
            imgIcon.setImageResource(icon)
        }
    }

    class Builder {
        private var message: String = ""
        private var icon: Int = 0

        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        fun setIcon(@DrawableRes icon: Int): Builder {
            this.icon = icon
            return this
        }


        fun build(): LoadingDialog {
            return newInstance(message, icon)
        }
    }

}
