package com.app.blockchain.certnetwork.appcommon.dialog

import android.support.annotation.DrawableRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import com.app.blockchain.certnetwork.common.extension.bouncedAction
import timber.log.Timber

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

@Suppress("MayBeConstant")
object DialogManager {

    private val TAG_LOADING_DIALOG = "tag_loading_dialog"

    private var dialogLoading: DialogFragment? = null


    fun showLoadingDialog(fragmentManager: FragmentManager,
                          message: String = "",
                          @DrawableRes icon: Int = 0) {
        bouncedAction(TAG_LOADING_DIALOG, action = {
            if (!isLoadingDialogAdded(fragmentManager)) {
                try {
                    dialogLoading = LoadingDialog.Builder()
                            .setMessage(message)
                            .setIcon(icon)
                            .build()
                            .apply {
                                show(fragmentManager, TAG_LOADING_DIALOG)
                            }
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        })
    }


    fun isCanShow(fragmentManager: FragmentManager): Boolean {
        return !fragmentManager.isStateSaved
    }


    fun isLoadingDialogAdded(manager: FragmentManager): Boolean {
        val fragments = manager.fragments
        for (fragment in fragments) {
            if (fragment is LoadingDialog) {
                return true
            }
        }
        return false
    }

    fun dismissLoadingDialog(manager: FragmentManager) {
        val fragments = manager.fragments
        for (fragment in fragments) {
            if (fragment is LoadingDialog) {
                fragment.dismissAllowingStateLoss()
                dialogLoading = null
            }
        }
    }


}
