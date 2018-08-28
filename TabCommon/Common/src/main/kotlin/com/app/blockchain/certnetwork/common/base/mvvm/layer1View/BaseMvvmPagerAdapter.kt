package com.app.blockchain.certnetwork.common.base.mvvm.layer1View

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import java.lang.ref.WeakReference

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

abstract class BaseMvvmPagerAdapter : BasePagerAdapter {

    private var fm: WeakReference<FragmentManager>
    private var activity: WeakReference<FragmentActivity>? = null
    private var fragment: WeakReference<Fragment>? = null
    var context: Context? = null
        private set



    constructor(activity: FragmentActivity) : super(activity.supportFragmentManager) {
        this.context = activity
        this.fm = WeakReference(activity.supportFragmentManager)
        this.activity = WeakReference(activity)
        init()
    }

    constructor(fragment: Fragment) : super(fragment.childFragmentManager) {
        this.context = fragment.context
        this.fm = WeakReference(fragment.childFragmentManager)
        this.fragment = WeakReference(fragment)
        init()
    }

    fun init() {
        onSetupViewModel()
    }

    open fun onSetupViewModel() {
    }

    fun <VM : ViewModel> getViewModel(viewModelClass: Class<VM>): VM {
        return if (fragment != null) {
            ViewModelProviders.of(fragment?.get()!!)
                    .get(viewModelClass)
        } else {
            ViewModelProviders.of(activity?.get()!!)
                    .get(viewModelClass)
        }
    }

    fun <VM : ViewModel> getViewModel(key: String, viewModelClass: Class<VM>): VM {
        return if (fragment != null) {
            ViewModelProviders.of(fragment?.get()!!)
                    .get(key, viewModelClass)
        } else {
            ViewModelProviders.of(activity?.get()!!)
                    .get(key, viewModelClass)
        }
    }


    fun <VM : ViewModel> getSharedViewModel(viewModelClass: Class<VM>): VM? {
        if (activity == null) return null
        return ViewModelProviders.of(activity?.get()!!).get(viewModelClass)
    }

}
