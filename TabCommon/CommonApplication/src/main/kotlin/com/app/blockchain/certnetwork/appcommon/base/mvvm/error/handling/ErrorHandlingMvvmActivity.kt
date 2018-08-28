package com.app.blockchain.certnetwork.appcommon.base.mvvm.error.handling

import android.view.ViewGroup
import com.lib.nextwork.engine.steam.TriggerDataSteam
import com.lib.nextwork.engine.steam.TriggerObjectDataStream
import com.app.blockchain.certnetwork.appcommon.base.mvvm.animation.AnimationHelperMvvmActivity
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResource


/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

abstract class ErrorHandlingMvvmActivity
    : AnimationHelperMvvmActivity(),
        ErrorHandlingInterface {

    override
    var targetView: ViewGroup? = null


    fun <T> mapLiveDataErrorHandling(triggerLiveData: TriggerObjectDataStream<*, AppResource<T>>): TriggerDataSteam<*, AppResource<T>> {
        return triggerLiveData.map(ErrorHandlingFunction(this))
    }

}

