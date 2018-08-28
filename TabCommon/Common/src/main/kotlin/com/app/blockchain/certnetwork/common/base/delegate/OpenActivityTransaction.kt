package com.app.blockchain.certnetwork.common.base.delegate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.util.Pair
import android.view.View
import com.app.blockchain.certnetwork.common.extension.notnull

/**
 * Created by「 The Khaeng 」on 31 Aug 2017 :)
 */

class OpenActivityTransaction(
        private var isFinish: Boolean = false,
        private var bundle: Bundle? = null,
        private var requestCode: Int = NO_ASSIGN,
        private var flag: Int = NO_ASSIGN,
        private var isFinishAll: Boolean = false,
        private var enterAnimId: Int = NO_ASSIGN,
        private var exitAnimId: Int = NO_ASSIGN,
        private var sharedElement: Array<out Pair<View, String>>? = null) {


    companion object {
        private const val NO_ASSIGN = -1
    }


    fun setRequestCode(requestCode: Int): OpenActivityTransaction {
        this.requestCode = requestCode
        return this
    }

    fun setFlags(flag: Int): OpenActivityTransaction {
        this.flag = flag
        return this
    }

    fun setBundle(bundle: Bundle?): OpenActivityTransaction {
        this.bundle = bundle
        return this
    }

    fun setFinish(isFinish: Boolean): OpenActivityTransaction {
        this.isFinish = isFinish
        return this
    }

    fun setFinishAllPrevious(isFinishAll: Boolean): OpenActivityTransaction {
        this.isFinishAll = isFinishAll
        return this
    }

    fun setAnim(@AnimRes enterAnimId: Int, @AnimRes exitAnimId: Int): OpenActivityTransaction {
        this.enterAnimId = enterAnimId
        this.exitAnimId = exitAnimId
        return this
    }

    @SafeVarargs
    fun setPairTransition(vararg sharedElement: Pair<View, String>): OpenActivityTransaction {
        this.sharedElement = sharedElement
        return this
    }

    fun open(activity: Activity?, targetClass: Class<*>) {
        activity?.apply {
            val intent = Intent(this, targetClass)
            bundle?.let { intent.putExtras(it) }

            if (flag != NO_ASSIGN) {
                intent.addFlags(flag)
            }

            val options: ActivityOptionsCompat? = sharedElement?.let {
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this,
                        *sharedElement!!)
            }

            if (isFinish && !isFinishAll) finish()
            if (isFinishAll){
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            }



            if (requestCode == NO_ASSIGN) {
                startActivity(intent, options?.toBundle())
            } else {
                startActivityForResult(intent, requestCode, options?.toBundle())
            }


            if (enterAnimId != NO_ASSIGN || exitAnimId != NO_ASSIGN) {
                overridePendingTransition(enterAnimId, exitAnimId)
            }

            sharedElement = null
            bundle = null
        }
    }


    fun open(fragment: Fragment?, targetClass: Class<*>) {
        fragment?.apply {
            val intent = Intent(this.context, targetClass)
            bundle?.let { intent.putExtras(it) }

            var options: ActivityOptionsCompat? = null
            kotlin.Pair(sharedElement, activity).notnull { sharedElement, activity ->
                options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        *sharedElement)

            }

            if (flag != NO_ASSIGN) {
                intent.addFlags(flag)
            }

            if (isFinish && !isFinishAll) fragment.activity?.finish()
            if (isFinishAll){
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        or Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TASK )
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            }

            if (enterAnimId != NO_ASSIGN || exitAnimId != NO_ASSIGN) {
                activity?.overridePendingTransition(enterAnimId, exitAnimId)
            }


            if (requestCode == NO_ASSIGN) {
                startActivity(intent, options?.toBundle())
            } else {
                startActivityForResult(intent, requestCode, options?.toBundle())
            }



            sharedElement = null
            bundle = null
        }
    }

}
