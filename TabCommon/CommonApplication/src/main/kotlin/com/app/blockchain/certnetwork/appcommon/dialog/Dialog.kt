package com.app.blockchain.certnetwork.appcommon.dialog

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.app.blockchain.certnetwork.appcommon.R

/**
 * Created by「 The Khaeng 」on 21 Oct 2017 :)
 */

// copy paste
//fun FragmentActivity.showLoading() = DialogManager.
//fun Fragment.showLoading() = DialogManager.


fun FragmentActivity.showLoading(message: String = getString(R.string.dialog_loading_title), icon: Int = 0) = DialogManager.showLoadingDialog(this.supportFragmentManager, message, icon)

fun Fragment.showLoading(message: String = getString(R.string.dialog_loading_title), icon: Int = 0) = this.fragmentManager?.let { DialogManager.showLoadingDialog(this.childFragmentManager, message, icon) }


fun FragmentActivity.hideLoading() = DialogManager.dismissLoadingDialog(this.supportFragmentManager)
fun Fragment.hideLoading() = DialogManager.dismissLoadingDialog(this.childFragmentManager)


