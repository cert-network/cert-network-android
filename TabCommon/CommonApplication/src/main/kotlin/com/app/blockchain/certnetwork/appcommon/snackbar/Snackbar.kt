package com.app.blockchain.certnetwork.appcommon.snackbar

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import com.app.blockchain.certnetwork.appcommon.R

/**
 * Created by「 The Khaeng 」on 21 Oct 2017 :)
 */


fun FragmentActivity.showSnackbarSuccess(message: String, targetView: View = this.findViewById(android.R.id.content)) = SnackbarManager.showSnackbarSuccess(targetView, message)
fun Fragment.showSnackbarSuccess(message: String, targetView: View? = this.view) = targetView?.let { SnackbarManager.showSnackbarSuccess(it, message) }

fun FragmentActivity.showSnackbarCaution(message: String, targetView: View = this.findViewById(android.R.id.content)) = SnackbarManager.showSnackbarCaution(targetView, message)
fun Fragment.showSnackbarCaution(message: String, targetView: View? = this.view) = targetView?.let { SnackbarManager.showSnackbarCaution(it, message) }

fun FragmentActivity.showSnackbarInfo(message: String, targetView: View = this.findViewById(android.R.id.content)) = SnackbarManager.showSnackbarInfo(targetView, message)
fun Fragment.showSnackbarInfo(message: String, targetView: View? = this.view) = targetView?.let { SnackbarManager.showSnackbarInfo(it, message) }

fun FragmentActivity.showSnackbarError(message: String, targetView: View = this.findViewById(android.R.id.content)) = SnackbarManager.showSnackbarError(targetView, message)
fun Fragment.showSnackbarError(message: String, targetView: View? = this.view) = targetView?.let { SnackbarManager.showSnackbarError(it, message) }

fun FragmentActivity.showSnackbarServiceUnavailable(targetView: View = this.findViewById(android.R.id.content)) = SnackbarManager.showSnackbarError(targetView, getString(R.string.alert_service_unavailable))
fun Fragment.showSnackbarServiceUnavailable(targetView: View? = this.view) = targetView?.let { SnackbarManager.showSnackbarError(it, getString(R.string.alert_service_unavailable)) }

fun FragmentActivity.showSnackbarErrorDismiss(message: String, targetView: View = this.findViewById(android.R.id.content)) = SnackbarManager.showSnackbarErrorDismiss(targetView, message)
fun Fragment.showSnackbarErrorDismiss(message: String, targetView: View? = this.view) = targetView?.let { SnackbarManager.showSnackbarErrorDismiss(it, message) }

fun FragmentActivity.showSnackbarMessage(message: String, targetView: View = this.findViewById(android.R.id.content)) = SnackbarManager.showSnackbarMessage(targetView, message)
fun Fragment.showSnackbarMessage(message: String, targetView: View? = this.view) = targetView?.let { SnackbarManager.showSnackbarMessage(it, message) }

fun FragmentActivity.showSnackbarMessageDismiss(message: String, targetView: View = this.findViewById(android.R.id.content)) = SnackbarManager.showSnackbarMessageDismiss(targetView, message)
fun Fragment.showSnackbarMessageDismiss(message: String, targetView: View? = this.view) = targetView?.let { SnackbarManager.showSnackbarMessageDismiss(it, message) }



