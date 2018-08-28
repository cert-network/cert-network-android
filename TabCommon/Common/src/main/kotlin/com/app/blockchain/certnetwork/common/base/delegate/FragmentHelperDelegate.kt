package com.app.blockchain.certnetwork.common.base.delegate

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.app.blockchain.certnetwork.common.R
import timber.log.Timber
import java.lang.ref.WeakReference


class FragmentHelperDelegate(
        fragmentActivity: FragmentActivity)
    : FragmentHelper, LifecycleObserver {
    private val lifecycle: Lifecycle = fragmentActivity.lifecycle
    private var containerLayoutRes = -1
    private var fragmentActivity: WeakReference<FragmentActivity> = WeakReference(fragmentActivity)
    private var isCanCommit = false

    private val view: FragmentActivity
        get() = fragmentActivity.get()!!


    private val visibleFragment: Fragment?
        get() {
            val fragmentManager = view.supportFragmentManager
            val fragments = fragmentManager.fragments
            fragments
                    .filter { it?.isVisible == true }
                    .forEach { return it }
            return null
        }


    override
    fun getCurrentFragment(): Fragment? = visibleFragment

    init {
        lifecycle.addObserver(this)
    }

    fun setCanCommit(canCommit: Boolean) {
        isCanCommit = canCommit
    }

    fun isCanCommit(fragment: Fragment): Boolean {
        return !isSameFragment(fragment) && isCanCommit
    }

    fun logAvailableFragment() {
        Timber.d(javaClass.simpleName, "fragment size: " + view.supportFragmentManager.fragments.size)
        Timber.d(javaClass.simpleName, "START Fragment Lists")
        view.supportFragmentManager.fragments
                .filter { it != null && it.tag != null }
                .forEach { Timber.d(javaClass.simpleName, "Tag: " + it.tag) }
        Timber.d(javaClass.simpleName, "END Fragment Lists")
    }

    override
    fun bindFragmentContainer(containerLayoutRes: Int) {
        this.containerLayoutRes = containerLayoutRes
    }

    override
    fun initFragment(fragment: Fragment, savedInstanceState: Bundle?) {
        throwNullIfNotBind()
        if (savedInstanceState == null) {
            createTransaction()
                    .setContainerLayout(containerLayoutRes)
                    .add(view, fragment)
        }
    }

    override
    fun contain(tag: String): Boolean {
        if (view.supportFragmentManager.fragments.size == 0) {
            return false
        }
        return view.supportFragmentManager.fragments.any { it?.tag?.contains(tag) == true }
    }

    override
    fun clearBackStackFragmentTo(index: Int) {
        if (view.supportFragmentManager.backStackEntryCount > 0) {
            val count = view.supportFragmentManager.backStackEntryCount
            for (i in 0 until count) {
                view.supportFragmentManager.popBackStackImmediate()
                if (i == count - index) break
            }
        }
    }

    override
    fun clearAllBackStacksFragment() {
        if (view.supportFragmentManager.backStackEntryCount > 0) {
            clearBackStackFragmentTo(0)
        }

    }

    override
    fun backPressedFragment() {
        view.supportFragmentManager.popBackStack()
    }

    override
    fun backPressedToFragment(tag: String) {
        if (contain(tag)) {
            view.supportFragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override
    fun openFragmentSlideFromLeft(fragment: Fragment, withFinish: Boolean) {
        if (!isCanCommit(fragment)) return
        createTransaction()
                .setFinish(withFinish)
                .setContainerLayout(containerLayoutRes)
                .setOpenAnim(R.anim.fragment_action_pop_slide_another_from_left_in, R.anim.fragment_action_pop_slide_current_to_right_out)
                .setPopAnim(R.anim.fragment_action_open_slide_another_from_right_in, R.anim.fragment_action_open_slide_current_to_left_out)
                .addToBackStack(fragment.javaClass.simpleName)
                .open(view, fragment)
    }


    override
    fun openFragmentSlideFromRight(fragment: Fragment, withFinish: Boolean) {
        if (!isCanCommit(fragment)) return
        createTransaction()
                .setFinish(withFinish)
                .setContainerLayout(containerLayoutRes)
                .setOpenAnim(R.anim.fragment_action_open_slide_another_from_right_in, R.anim.fragment_action_open_slide_current_to_left_out)
                .setPopAnim(R.anim.fragment_action_pop_slide_another_from_left_in, R.anim.fragment_action_pop_slide_current_to_right_out)
                .addToBackStack(fragment.javaClass.simpleName)
                .open(view, fragment)
    }

    override
    fun openFragment(fragment: Fragment, withFinish: Boolean) {
        if (!isCanCommit(fragment)) return
        createTransaction()
                .setFinish(withFinish)
                .setContainerLayout(containerLayoutRes)
                .addToBackStack(fragment.javaClass.simpleName)
                .open(view, fragment)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroyView() {
        lifecycle.removeObserver(this)
    }

    fun handleBackButton() {
        if (view.supportFragmentManager.backStackEntryCount > 1) {
            view.supportFragmentManager.popBackStack()
        } else {
            view.finish()
        }
    }

    fun createTransaction(): OpenFragmentTransaction {
        return OpenFragmentTransaction()
    }
    /* ============================ Private method ============================================== */

    private fun isSameFragment(fragment: Fragment): Boolean {
        val currentFragment = getCurrentFragment()
        return null != currentFragment && currentFragment.javaClass == fragment.javaClass
    }

    private fun throwNullIfNotBind() {
        if (containerLayoutRes == -1)
            throw NullPointerException("bindFragmentContainer() first.")
    }

}
