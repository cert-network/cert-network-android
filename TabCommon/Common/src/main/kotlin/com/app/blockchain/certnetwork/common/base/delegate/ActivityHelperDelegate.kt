package com.app.blockchain.certnetwork.common.base.delegate

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import java.lang.ref.WeakReference

/**
 * Created by「 The Khaeng 」on 31 Aug 2017 :)
 */

class ActivityHelperDelegate
    : ActivityHelper {

    private var fragment: WeakReference<Fragment>? = null
    private var activity: WeakReference<Activity>? = null

    constructor(activity: FragmentActivity) {
        this.activity = WeakReference(activity)
    }

    constructor(fragment: Fragment) {
        this.fragment = WeakReference(fragment)
    }

    fun createTransaction(): OpenActivityTransaction {
        return OpenActivityTransaction()
    }

    override
    fun openActivity(targetClass: Class<*>,
                     request: Int,
                     data: Bundle?,
                     flags: Int) {
        val openActivityTransaction = createTransaction()
                .setBundle(data)
                .setRequestCode(request)
                .setFlags(flags)
        open(openActivityTransaction, targetClass)
    }


    override
    fun openActivityWithFinish(targetClass: Class<*>, data: Bundle?) {
        val openActivityTransaction = createTransaction()
                .setBundle(data)
                .setFinish(true)
        open(openActivityTransaction, targetClass)
    }


    override
    fun openActivityWithAllFinish(targetClass: Class<*>, data: Bundle?) {
        val openActivityTransaction = createTransaction()
                .setBundle(data)
                .setFinishAllPrevious(true)
        open(openActivityTransaction, targetClass)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroyView() {
        activity = null
        fragment = null
    }

    /* =========================== Private method ============================================= */
    private fun open(openActivityTransaction: OpenActivityTransaction, targetClass: Class<*>) {
        when {
            getActivity() != null -> openActivityTransaction.open(getActivity(), targetClass)
            getFragment() != null -> openActivityTransaction.open(getFragment(), targetClass)
        }
    }


    private fun getActivity(): Activity? {
        return activity?.get()
    }

    private fun getFragment(): Fragment? {
        return fragment?.get()
    }
}
