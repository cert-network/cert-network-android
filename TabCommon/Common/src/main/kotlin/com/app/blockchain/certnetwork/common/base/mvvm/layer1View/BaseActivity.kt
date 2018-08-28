package com.app.blockchain.certnetwork.common.base.mvvm.layer1View

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.app.blockchain.certnetwork.common.BuildConfig
import com.app.blockchain.certnetwork.common.base.delegate.*
import com.app.blockchain.certnetwork.common.base.mvvm.exception.NotSetLayoutException
import com.app.blockchain.certnetwork.common.base.utils.android.ScreenOrientationHelper
import timber.log.Timber


/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

abstract class BaseActivity
    : AppCompatActivity(),
        ActivityHelper,
        FragmentHelper,
        ScreenOrientationHelper.ScreenOrientationChangeListener {

    companion object {
        const val NO_LAYOUT = -2
    }

    private val helper = ScreenOrientationHelper()
    private lateinit var activityOpener: ActivityHelperDelegate
    private lateinit var fragmentDelegate: FragmentHelperDelegate
    private var viewPager: ViewPager? = null
    var isRestoreInstanceStated: Boolean = false

    val rootView: View get() = window.decorView.rootView

    val decorView: View get() = window.decorView

    override
    fun onCreate(savedInstanceState: Bundle?) {
        activityOpener = ActivityHelperDelegate(this)
        fragmentDelegate = FragmentHelperDelegate(this)
        helper.setActivity(this)
        helper.onCreate(savedInstanceState)
        helper.setScreenOrientationChangeListener(this)
        onPreCreate(savedInstanceState)
        val intent = intent
        if (intent != null) {
            val bundle = intent.extras
            if (bundle != null) {
                Timber.d("onIntentExtras: extras=$bundle")
                onIntentExtras(bundle, savedInstanceState)
            }
        }
        super.onCreate(savedInstanceState)
        val layoutResId = setupLayoutView()
        if (layoutResId != NO_LAYOUT && setupLayoutView() == 0) throw NotSetLayoutException()
        Timber.d("setContentView: ")
        if (layoutResId != NO_LAYOUT) {
            setContentView(layoutResId)
        }
        Timber.d("onBindView: ")
        onBindView(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Timber.d("onDebuggingMode: ")
            onDebuggingMode(savedInstanceState)
        }
        Timber.d("onInitialize: ")
        onInitialize(savedInstanceState)
        if (savedInstanceState == null) {
            Timber.d("onPrepareInstance")
            onPrepareInstance()
        }
        Timber.d("onSetupView: ")
        onSetupView(savedInstanceState)
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
    }

    override
    fun onPostResume() {
        Timber.d("onPostResume: ")
        super.onPostResume()
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
    fun onDestroy() {
        Timber.d("onDestroy: ")
        super.onDestroy()
    }

    override
    fun onSaveInstanceState(outState: Bundle?) {
        Timber.d("onSaveInstanceState: outState=$outState")
        super.onSaveInstanceState(outState)
        helper.onSaveInstanceState(outState)
    }


    /**
     * @see onRestoreInstanceStates(Bundle)
     * @see onRestoreInstanceStatesToView(Bundle)
     */
    @Deprecated("use onRestoreInstanceStates(Bundle)")
    override
    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        isRestoreInstanceStated = true
        Timber.d("onRestoreInstanceStates: savedInstanceState=$savedInstanceState")
        super.onRestoreInstanceState(savedInstanceState)
        helper.onRestoreInstanceState(savedInstanceState)
        onRestoreInstanceStates(savedInstanceState)
        helper.checkOrientation()
        onRestoreInstanceStatesToView(savedInstanceState)
    }

    override
    fun onScreenOrientationChanged(orientation: Int) {
    }

    override
    fun onScreenOrientationChangedToLandscape() {

    }

    override
    fun onScreenOrientationChangedToPortrait() {

    }

    override
    fun getCurrentFragment(): Fragment? = fragmentDelegate.getCurrentFragment()


    fun registerOnBackPressedViewPager(viewPager: ViewPager?) {
        this.viewPager = viewPager
    }

    override
    fun onBackPressed() {
        val isBackPressedFragment: Boolean =
                if (viewPager != null) {
                    val adapter = viewPager?.adapter
                    val fragment = when (adapter) {
                        is BasePagerAdapter -> adapter.fragmentList[viewPager!!.currentItem]
                        else -> null
                    }
                    (fragment as? BaseFragment)?.onBackPressed() ?: false
                } else {
                    supportFragmentManager.fragments
                            .filterIsInstance(BaseFragment::class.java)
                            .filter { it.isHandleBackPressed() }
                            .lastOrNull { it.isVisible }
                            ?.onBackPressed() ?: false
                }

        if (!supportFragmentManager.popBackStackImmediate() && !isBackPressedFragment) {
            super.onBackPressed()
        }
    }


    open fun onRestoreInstanceStates(savedInstanceState: Bundle) {
        Timber.d("onRestoreInstanceStates: savedInstanceState=$savedInstanceState")
    }

    open fun onRestoreInstanceStatesToView(savedInstanceState: Bundle) {
        Timber.d("onRestoreInstanceStatesToView: savedInstanceState=$savedInstanceState")
    }

    open fun onIntentExtras(bundle: Bundle, savedInstanceState: Bundle?) {
        Timber.d("onIntentExtras: bundle=$bundle")
    }

    open fun createActivityTransaction(): OpenActivityTransaction {
        return activityOpener.createTransaction()
    }

    open fun createFragmentTransaction(): OpenFragmentTransaction {
        return fragmentDelegate.createTransaction()
    }

    abstract fun setupLayoutView(): Int

    open fun onPreCreate(savedInstanceState: Bundle?) {}

    open fun onBindView(savedInstanceState: Bundle?) {}

    open fun onDebuggingMode(savedInstanceState: Bundle?) {}

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
        fragmentDelegate.bindFragmentContainer(containerLayoutRes)
    }

    override
    fun initFragment(fragment: Fragment, savedInstanceState: Bundle?) {
        fragmentDelegate.initFragment(fragment, savedInstanceState)
    }

    override
    fun contain(tag: String): Boolean {
        return fragmentDelegate.contain(tag)
    }

    override
    fun clearBackStackFragmentTo(index: Int) {
        fragmentDelegate.clearBackStackFragmentTo(index)
    }

    override
    fun clearAllBackStacksFragment() {
        fragmentDelegate.clearAllBackStacksFragment()
    }

    override
    fun backPressedFragment() {
        fragmentDelegate.backPressedFragment()
    }

    override
    fun backPressedToFragment(tag: String) {
        fragmentDelegate.backPressedToFragment(tag)
    }

    override
    fun openFragmentSlideFromLeft(fragment: Fragment, withFinish: Boolean) {
        fragmentDelegate.openFragmentSlideFromLeft(fragment, withFinish)
    }

    override
    fun openFragmentSlideFromRight(fragment: Fragment, withFinish: Boolean) {
        fragmentDelegate.openFragmentSlideFromRight(fragment, withFinish)
    }

    override
    fun openFragment(fragment: Fragment, withFinish: Boolean) {
        fragmentDelegate.openFragment(fragment, withFinish)
    }

}
