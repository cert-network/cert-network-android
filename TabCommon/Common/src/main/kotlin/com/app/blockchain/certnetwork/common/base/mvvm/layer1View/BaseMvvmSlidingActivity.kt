package com.app.blockchain.certnetwork.common.base.mvvm.layer1View

import android.os.Bundle

/**
 * Created by「 The Khaeng 」on 27 Aug 2017 :)
 */

abstract class BaseMvvmSlidingActivity : BaseMvvmActivity() {

//    lateinit var slidingMenu: SlidingMenu

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setupSlidingMenuFragment(savedInstanceState)
    }

//    private fun setupSlidingMenuFragment(savedInstanceState: Bundle?) {
//        slidingMenu = SlidingMenu(this)
//        setupMenu(slidingMenu)
//        if (savedInstanceState == null) {
//            val fragmentTransaction = this.supportFragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.container_side_menu, setupSlidingMenuFragment())
//            fragmentTransaction.commit()
//        }
//    }
//
//    open fun setupMenu(menu: SlidingMenu) {
//        menu.setMenu(R.layout.container_slide_menu)
//        menu.touchModeAbove = SlidingMenu.TOUCHMODE_MARGIN
//        menu.setShadowWidthRes(R.dimen.md_elevation_card)
//        menu.setShadowDrawable(R.drawable.shadow_left)
//        menu.setBehindOffsetRes(R.dimen.md_margin_drawer_navigation)
//        menu.setFadeDegree(0.35f)
//        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT, true)
//    }
//
//    fun toggleMenu() {
//        slidingMenu.toggle()
//    }
//
//    protected abstract fun setupSlidingMenuFragment(): Fragment
}

