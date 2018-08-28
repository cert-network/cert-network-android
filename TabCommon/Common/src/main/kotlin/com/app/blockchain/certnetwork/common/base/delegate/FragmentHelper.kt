package com.app.blockchain.certnetwork.common.base.delegate

import android.os.Bundle
import android.support.v4.app.Fragment

/**
* Created by「 The Khaeng 」on 08 Oct 2017 :)
*/

interface FragmentHelper {

    fun getCurrentFragment(): Fragment?

    fun bindFragmentContainer(containerLayoutRes: Int)

    fun initFragment(fragment: Fragment, savedInstanceState: Bundle?)

    fun contain(tag: String): Boolean

    fun clearBackStackFragmentTo(index: Int)

    fun clearAllBackStacksFragment()

    fun backPressedFragment()

    fun backPressedToFragment(tag: String)

    fun openFragmentSlideFromLeft(fragment: Fragment, withFinish: Boolean = false)

    fun openFragmentSlideFromRight(fragment: Fragment, withFinish: Boolean = false)

    fun openFragment(fragment: Fragment, withFinish: Boolean = false)
}
