package com.app.blockchain.certnetwork.appcommon.base.mvvm.animation

import android.animation.Animator
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import com.app.blockchain.certnetwork.common.base.mvvm.layer1View.BaseMvvmFragment
import com.transitionseverywhere.Transition


/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

@Suppress("PrivatePropertyName","UNUSED_PARAMETER")
abstract class AnimationHelperMvvmFragment
    : BaseMvvmFragment() {


    private var isPendingIntroAnimation: Boolean = false
    private var isAnimationEnded: Boolean = false
    private var isFirstRestore: Boolean = false

    private val KEY_INTRO_ANIMATION = "key_intro_animation"
    private val KEY_ANIMATION_RAN = "key_animation_ran"

    private var animationRanMap = HashMap<String, Boolean>()

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            isPendingIntroAnimation = true
        }
    }

    fun isAnimationRan(key: String): Boolean {
        if (animationRanMap[key] == true) {
            return animationRanMap[key]!!
        }

        animationRanMap[key] = true
        return false
    }

    override
    fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        if (isPendingIntroAnimation) {
            isPendingIntroAnimation = false
        }
    }

    override
    fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            onStartIntroAnimation()
        }
    }

    fun showFocusView(view: View) {
        // do nothing
    }


    fun addEnterTransition(transition: Transition) {
        sharedElementEnterTransition = transition
    }

    fun addExitTransition(transition: Transition) {
        sharedElementReturnTransition = transition
    }

    override
    fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_INTRO_ANIMATION, isAnimationEnded)
        outState.putSerializable(KEY_INTRO_ANIMATION, animationRanMap)
    }

    override
    fun onRestoreInstanceStates(savedInstanceState: Bundle) {
        super.onRestoreInstanceStates(savedInstanceState)
        isAnimationEnded = savedInstanceState.getBoolean(KEY_INTRO_ANIMATION, false)
        animationRanMap = savedInstanceState.getSerializable(KEY_INTRO_ANIMATION) as HashMap<String, Boolean>
        isFirstRestore = true
    }

    /**
     * Start intro animation.
     */
    open fun onStartIntroAnimation() {}

    val animatorStack: MutableList<Animator> = mutableListOf()

    fun addStackAnimation(animator: Animator) {
        animator.addListener(object : Animator.AnimatorListener {
            override
            fun onAnimationRepeat(animation: Animator?) {
            }

            override
            fun onAnimationEnd(animation: Animator?) {
                animatorStack.remove(animator)
                startAnimationInStack()
            }

            override
            fun onAnimationCancel(animation: Animator?) {
            }

            override
            fun onAnimationStart(animation: Animator?) {
            }

        })
        animatorStack.add(animator)
        startAnimationInStack()
    }

    private fun startAnimationInStack() {
        if (animatorStack.isNotEmpty()) {
            if (!animatorStack[0].isRunning) {
                animatorStack[0].start()
            }
        }
    }

    fun isIntroAnimationEnded(): Boolean {
        if (!isAnimationEnded) {
            isAnimationEnded = true
            return false
        }
        return isAnimationEnded
    }


    fun isFirstRestoreAnimation(): Boolean {
        if (isFirstRestore) {
            isFirstRestore = false
            return true
        }
        return isFirstRestore
    }
}