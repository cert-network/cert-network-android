package com.app.blockchain.certnetwork.common.extension

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

fun Activity?.clickToHideKeyboard(view: View?) {
    view?.setOnTouchListener { _, event ->
        when {
            event.action == MotionEvent.ACTION_DOWN -> true
            event.action == MotionEvent.ACTION_UP -> {
                hideKeyboard()
                false
            }
            else -> false
        }
    }
}


fun Activity?.hideKeyboard(view: View?) {
    if (view != null) {
        val imm = this?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }
}

fun Activity?.showKeyboard() {
    var view = this?.currentFocus
    if (view == null) view = View(this)
    val imm = this?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}


fun Activity?.hideKeyboard() {
    var view = this?.currentFocus
    if (view == null) view = View(this)
    val imm = this?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View?.showKeyboard() {
    this?.isFocusable = true
    this?.isFocusableInTouchMode = true
    this?.requestFocus()
    val imm = this?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}


fun View?.hideKeyboard() {
    val imm = this?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(this?.windowToken, 0)
}

fun Context?.toggleKeyboard() {
    val imm = this?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

