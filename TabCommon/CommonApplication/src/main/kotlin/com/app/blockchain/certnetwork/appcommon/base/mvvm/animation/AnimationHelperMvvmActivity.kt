package com.app.blockchain.certnetwork.appcommon.base.mvvm.animation

import android.app.Activity
import android.app.ActivityManager
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.transition.Transition
import android.view.Menu
import android.view.View
import com.app.blockchain.certnetwork.appcommon.R
import com.app.blockchain.certnetwork.common.base.mvvm.layer1View.BaseMvvmActivity

/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

abstract class AnimationHelperMvvmActivity
    : BaseMvvmActivity() {

    private val KEY_INTRO_ANIMATION = "key_intro_animation"

    val isFinishAfterTransition: Boolean = true

    var isPendingIntroAnimation: Boolean = false

    private var animationRanMap = HashMap<String, Boolean>()


    override
    fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            isPendingIntroAnimation = true
            setOverridePendingStartTransition()
        }
        super.onCreate(savedInstanceState)

    }

    fun isAnimationRan(key: String): Boolean {
        if (animationRanMap[key] == true) {
            return animationRanMap[key]!!
        }

        animationRanMap[key] = true
        return false
    }

    override
    fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(KEY_INTRO_ANIMATION, animationRanMap)
    }

    @Suppress("UNCHECKED_CAST")
    override
    fun onRestoreInstanceStates(savedInstanceState: Bundle) {
        super.onRestoreInstanceStates(savedInstanceState)
        animationRanMap = savedInstanceState.getSerializable(KEY_INTRO_ANIMATION) as HashMap<String, Boolean>
    }

    override
    fun onBackPressed() {
        super.onBackPressed()
        if (!isTaskRoot) {
            setOverridePendingEndTransition()
        }
    }

    override
    fun finish() {
        super.finish()
        if (!isTaskRoot) {
            setOverridePendingEndTransition()
        }
    }

    open fun setOverridePendingStartTransition() {
        this.overridePendingTransition(
                R.anim.activity_action_open_slide_another_from_right_in,
                R.anim.activity_action_open_slide_current_to_left_out)

    }

    open fun setOverridePendingEndTransition() {
        this.overridePendingTransition(
                R.anim.activity_action_finish_slide_another_from_left_in,
                R.anim.activity_action_finish_slide_current_to_right_out)
    }

    fun overridePendingDefaultTransition() {}

    fun overridePendingFadeTransition() {
        this.overridePendingTransition(
                R.anim.fragment_action_open_slide_another_from_right_in,
                R.anim.fragment_action_open_slide_current_to_left_out)
    }

    override
    fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        if (isPendingIntroAnimation) {
            isPendingIntroAnimation = false
            startIntroAnimation()
        }
        return true
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected fun setTaskDescription(label: String, icon: Bitmap?, colorPrimary: Int) {
        val taskDesc = ActivityManager.TaskDescription(
                label,
                icon,
                colorPrimary)
        setTaskDescription(taskDesc)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun addEnterTransition(transition: Transition) {
        window.sharedElementEnterTransition = transition

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun addExitTransition(transition: Transition) {
        window.sharedElementReturnTransition = transition
    }


    override
    fun backPressedFragment() {
        setResult(Activity.RESULT_OK)
        if (isFinishAfterTransition) {
            supportFinishAfterTransition()
        } else {
            finish()
        }
    }

    fun onBackPressed(data: Bundle) {
        val resultData = Intent()
        resultData.putExtras(data)
        setResult(Activity.RESULT_OK, resultData)
        if (isFinishAfterTransition) {
            supportFinishAfterTransition()
        } else {
            finish()
        }
    }

    fun showFocusView(view: View) {
        // do nothing
    }

    /**
     * Start intro animation.
     */
    protected fun startIntroAnimation() {}


}