package com.app.blockchain.certnetwork.appcommon.snackbar

import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.view.View
import com.danimahardhika.cafebar.CafeBar
import com.danimahardhika.cafebar.CafeBarDuration
import com.danimahardhika.cafebar.CafeBarTheme
import com.app.blockchain.certnetwork.appcommon.R

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

@Suppress("MayBeConstant")
object SnackbarManager {
    val VERY_SHORT = 100
    val SHORT = CafeBarDuration.SHORT.duration
    val MEDIUM = CafeBarDuration.MEDIUM.duration
    val DEFAULT_DURATION = CafeBarDuration.MEDIUM.duration


    fun showSnackbarSuccess(targetView: View, message: String, duration: Int = MEDIUM) {
        createSuccessBuilder(targetView)
                ?.duration(duration)
                ?.autoDismiss(true)
                ?.content(message)
                ?.show()
    }


    fun showSnackbarInfo(targetView: View, message: String, duration: Int = MEDIUM) {
        createInfoBuilder(targetView)
                ?.duration(duration)
                ?.autoDismiss(true)
                ?.content(message)
                ?.show()
    }


    fun showSnackbarCaution(targetView: View, message: String, duration: Int = MEDIUM) {
        createCautionBuilder(targetView)
                ?.duration(duration)
                ?.autoDismiss(true)
                ?.content(message)
                ?.show()
    }


    fun showSnackbarError(targetView: View, message: String, duration: Int = MEDIUM) {
        createErrorBuilder(targetView)
                ?.duration(duration)
                ?.autoDismiss(true)
                ?.content(message)
                ?.show()
    }

    fun showSnackbarMessage(targetView: View, message: String, duration: Int = MEDIUM) {
        createMessageBuilder(targetView)
                ?.duration(duration)
                ?.autoDismiss(true)
                ?.content(message)
                ?.show()
    }


    fun showSnackbarSuccessDismiss(targetView: View, message: String) {
        createSuccessBuilder(targetView)
                ?.autoDismiss(false)
                ?.content(message)
                ?.neutralText(R.string.dismiss)
                ?.neutralColor(getColor(targetView.context, R.color.snackbar_text))
                ?.show()
    }


    fun showSnackbarInfoDismiss(targetView: View, message: String) {
        createInfoBuilder(targetView)
                ?.autoDismiss(false)
                ?.content(message)
                ?.neutralText(R.string.dismiss)
                ?.neutralColor(getColor(targetView.context, R.color.snackbar_text))
                ?.show()
    }


    fun showSnackbarCautionDismiss(targetView: View, message: String) {
        createCautionBuilder(targetView)
                ?.autoDismiss(false)
                ?.content(message)
                ?.neutralText(R.string.dismiss)
                ?.neutralColor(getColor(targetView.context, R.color.snackbar_text))
                ?.show()
    }


    fun showSnackbarErrorDismiss(targetView: View, message: String) {
        createErrorBuilder(targetView)
                ?.autoDismiss(false)
                ?.content(message)
                ?.neutralText(R.string.dismiss)
                ?.neutralColor(getColor(targetView.context, R.color.snackbar_text))
                ?.show()
    }

    fun showSnackbarMessageDismiss(targetView: View, message: String) {
        createMessageBuilder(targetView)
                ?.autoDismiss(false)
                ?.content(message)
                ?.neutralText(R.string.dismiss)
                ?.neutralColor(getColor(targetView.context, R.color.snackbar_text))
                ?.show()
    }

    /* =========================== Private method =============================================== */
    private fun createDefaultBuilder(targetView: View): CafeBar.Builder? {
        return targetView.let {
            CafeBar.builder(it.context)
                    .to(targetView)
                    .fitSystemWindow()
        }
    }

    private fun createSuccessBuilder(targetView: View): CafeBar.Builder? {
        return createCustomBuilder(targetView,
                R.color.alert_snackbar_success,
                R.drawable.ic_snackbar_success_white)
    }

    private fun createInfoBuilder(targetView: View): CafeBar.Builder? {
        return createCustomBuilder(targetView,
                R.color.alert_snackbar_info,
                R.drawable.ic_snackbar_info_white)
    }

    private fun createCautionBuilder(targetView: View): CafeBar.Builder? {
        return createCustomBuilder(targetView,
                R.color.alert_snackbar_caution,
                R.drawable.ic_snackbar_caution_white)
    }

    private fun createErrorBuilder(targetView: View): CafeBar.Builder? {
        return createCustomBuilder(targetView,
                R.color.alert_snackbar_error,
                R.drawable.ic_snackbar_error_white)
    }

    private fun createMessageBuilder(targetView: View): CafeBar.Builder? {
        return createDefaultBuilder(targetView)
                ?.theme(CafeBarTheme.Custom(
                        getColor(targetView.context, R.color.alert_snackbar_message)))
    }

    private fun createCustomBuilder(targetView: View,
                                    @ColorRes colorId: Int,
                                    @DrawableRes iconId: Int): CafeBar.Builder? {
        return createDefaultBuilder(targetView)
                ?.theme(CafeBarTheme.Custom(
                        getColor(targetView.context, colorId)))
                ?.icon(iconId, false)
    }

    private fun getColor(context: Context, @ColorRes colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }


}
