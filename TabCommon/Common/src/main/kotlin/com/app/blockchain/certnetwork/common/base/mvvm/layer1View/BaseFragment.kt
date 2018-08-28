package com.app.blockchain.certnetwork.common.base.mvvm.layer1View

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import com.app.blockchain.certnetwork.common.BuildConfig
import com.app.blockchain.certnetwork.common.base.delegate.*
import com.app.blockchain.certnetwork.common.base.mvvm.exception.NotSetLayoutException
import com.app.blockchain.certnetwork.common.base.utils.android.ScreenOrientationHelper
import com.app.blockchain.certnetwork.common.extension.notnull
import timber.log.Timber

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

abstract class BaseFragment
    : Fragment(),
        ActivityHelper,
        FragmentHelper,
        ScreenOrientationHelper.ScreenOrientationChangeListener {

    private val helper = ScreenOrientationHelper()
    private lateinit var activityOpener: ActivityHelperDelegate
    private lateinit var fragmentDelegate: FragmentHelperDelegate
    var isRestoreInstanceStated: Boolean = false

    private var windowsInset: WindowInsets? = null


    override
    fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onPreCreate: savedInstanceState=$savedInstanceState")
        activityOpener = ActivityHelperDelegate(this)
        activity?.let {
            fragmentDelegate = FragmentHelperDelegate(it)
            fragmentDelegate.setCanCommit(false)
        }
        onPreCreate(savedInstanceState)
        Timber.d("onCreate: savedInstanceState=$savedInstanceState")
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            Timber.d("onArguments: arguments=$bundle")
            onArguments(bundle)
        }

        if (savedInstanceState != null) {
            Timber.d("onRestoreInstanceStates: savedInstanceState=$savedInstanceState")
            onRestoreInstanceStates(savedInstanceState)
        }
    }

    override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.d("onCreateView: savedInstanceState=$savedInstanceState")
        val layoutResId = setupLayoutView()
        if (setupLayoutView() == 0) throw NotSetLayoutException()
        return inflater.inflate(layoutResId, container, false)
    }


    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            windowsInset?.let {
                view.onApplyWindowInsets(it)
                windowsInset = null
            }
        }
        Timber.d("onViewCreated: savedInstanceState=$savedInstanceState")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            windowsInset?.let { view.onApplyWindowInsets(it) }
        }
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onBindView")
        onBindView(view)

        if (BuildConfig.DEBUG) {
            Timber.d("onDebuggingMode: ")
            onDebuggingMode()
        }
        Pair(activity, savedInstanceState).notnull { activity, savedState ->
            helper.setActivity(activity)
            helper.onCreate(savedState)
            helper.setScreenOrientationChangeListener(this)
            helper.onRestoreInstanceState(savedState)
            helper.checkOrientation()
        }
        Timber.d("onInitialize")
        onInitialize(savedInstanceState)
        if (savedInstanceState == null) {
            Timber.d("onPrepareInstance")
            onPrepareInstance()
        }
        Timber.d("onSetupView")
        onSetupView(savedInstanceState)
        if (savedInstanceState != null) {
            Timber.d("onRestoreInstanceStatesToView: savedInstanceState=$savedInstanceState")
            onRestoreInstanceStatesToView(savedInstanceState)
        }
    }

    @Suppress("NAME_SHADOWING")
    override
    fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override
    fun onStart() {
        Timber.d("onStart: ")
        super.onStart()
    }

    override
    fun onResume() {
        Timber.d("onResume: ")
        super.onResume()
        fragmentDelegate.setCanCommit(true)
    }

    override
    fun onPause() {
        Timber.d("onPause: ")
        super.onPause()
    }

    override
    fun onStop() {
        Timber.d("onStop: ")
        super.onStop()
        fragmentDelegate.setCanCommit(false)
    }


    override
    open fun onSaveInstanceState(outState: Bundle) {
        Timber.d("saveInstanceState: oustState=$outState")
        super.onSaveInstanceState(outState)
        helper.onSaveInstanceState(outState)
    }

    open fun setWindowsInsets(insets: WindowInsets?) {
        windowsInset = insets
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            if (windowsInset != null && view != null) {
                view?.onApplyWindowInsets(windowsInset)
                windowsInset = null
            }
        }
    }

    fun getWindowsInset(): WindowInsets? {
        return windowsInset
    }


    open fun onRestoreInstanceStates(savedInstanceState: Bundle) {
        Timber.d("onRestoreInstanceStates: savedInstanceState=$savedInstanceState")
        isRestoreInstanceStated = true
    }

    open fun onRestoreInstanceStatesToView(savedInstanceState: Bundle) {
        Timber.d("onRestoreInstanceStatesToView: savedInstanceState=$savedInstanceState")
    }

    open fun createActivityTransaction(): OpenActivityTransaction {
        return activityOpener.createTransaction()
    }

    open fun createFragmentTransaction(): OpenFragmentTransaction {
        return fragmentDelegate.createTransaction()
    }

    open fun onArguments(bundle: Bundle) {
        Timber.d("onIntentExtras: bundle=$bundle")
    }

    override
    fun onScreenOrientationChanged(orientation: Int) {

    }

    override
    fun getCurrentFragment(): Fragment? = fragmentDelegate.getCurrentFragment()

    override
    fun onScreenOrientationChangedToPortrait() {

    }

    override
    fun onScreenOrientationChangedToLandscape() {

    }


    open fun isHandleBackPressed(): Boolean {
        return true
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    abstract fun setupLayoutView(): Int

    open fun onPreCreate(savedInstanceState: Bundle?) {}

    open fun onDebuggingMode() {}

    open fun onBindView(view: View?) {}

    open fun onInitialize(savedInstanceState: Bundle?) {}

    open fun onSetupView(savedInstanceState: Bundle?) {}

    open fun onPrepareInstance() {}


    /* ============================ Open Activity ======================================== */
    override
    fun openActivity(targetClass: Class<*>, request: Int, data: Bundle?, flags: Int) {
        activityOpener.openActivity(targetClass, request, data, flags)
    }

    override
    fun openActivityWithFinish(targetClass: Class<*>, data: Bundle?) {
        activityOpener.openActivityWithFinish(targetClass, data)
    }

    override
    fun openActivityWithAllFinish(targetClass: Class<*>, data: Bundle?) {
        activityOpener.openActivityWithAllFinish(targetClass, data)
    }


    /* ============================ Open Fragment ======================================== */
    override
    fun bindFragmentContainer(containerLayoutRes: Int) {
        (activity as? BaseActivity)?.bindFragmentContainer(containerLayoutRes)
    }

    override
    fun initFragment(fragment: Fragment, savedInstanceState: Bundle?) {
        (activity as? BaseActivity)?.initFragment(fragment, savedInstanceState)
    }

    override
    fun contain(tag: String): Boolean {
        return (activity as? BaseActivity)?.contain(tag) ?: false
    }

    override
    fun clearBackStackFragmentTo(index: Int) {
        (activity as? BaseActivity)?.clearBackStackFragmentTo(index)
    }

    override
    fun clearAllBackStacksFragment() {
        (activity as? BaseActivity)?.clearAllBackStacksFragment()
    }

    override
    fun backPressedFragment() {
        (activity as? BaseActivity)?.backPressedFragment()
    }

    override
    fun backPressedToFragment(tag: String) {
        (activity as? BaseActivity)?.backPressedToFragment(tag)
    }

    override
    fun openFragmentSlideFromLeft(fragment: Fragment, withFinish: Boolean) {
        (activity as? BaseActivity)?.openFragmentSlideFromLeft(fragment, withFinish)
    }

    override
    fun openFragmentSlideFromRight(fragment: Fragment, withFinish: Boolean) {
        (activity as? BaseActivity)?.openFragmentSlideFromRight(fragment, withFinish)
    }


    override
    fun openFragment(fragment: Fragment, withFinish: Boolean) {
        (activity as? BaseActivity)?.openFragment(fragment, withFinish)
    }

}

