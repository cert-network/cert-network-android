package com.app.blockchain.certnetwork.common.extension

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

fun Context.toast(resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
}

fun Context.toastLong(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun View.toast(resId: Int) = context.toast(resId)

fun View.toast(text: String) = context.toast(text)

fun View.toastLong(resId: Int) = context.toastLong(resId)

fun View.toastLong(text: String) = context.toastLong(text)

fun Fragment.toast(resId: Int) = activity?.toast(resId)

fun Fragment.toast(text: String) = activity?.toast(text)

fun Fragment.toastLong(resId: Int) = activity?.toastLong(resId)

fun Fragment.toastLong(text: String) = activity?.toastLong(text)

