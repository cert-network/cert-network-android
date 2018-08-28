package com.app.blockchain.certnetwork.appcommon.base.mvvm.error.handling


import android.view.ViewGroup
import com.lib.nextwork.engine.steam.TriggerDataSteam
import com.app.blockchain.certnetwork.appcommon.base.mvvm.animation.AnimationHelperMvvmFragment
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResource

/**
 * Created by「 The Khaeng 」on 25 Aug 2017 :)
 */

abstract class ErrorHandlingMvvmFragment
    : AnimationHelperMvvmFragment(),
        ErrorHandlingInterface {

    override
    var targetView: ViewGroup? = null


    fun <T> mapLiveDataErrorHandling(triggerLiveData: TriggerDataSteam<*, AppResource<T>>): TriggerDataSteam<*, AppResource<T>> {
        return triggerLiveData.map(ErrorHandlingFunction(this))
    }



}

