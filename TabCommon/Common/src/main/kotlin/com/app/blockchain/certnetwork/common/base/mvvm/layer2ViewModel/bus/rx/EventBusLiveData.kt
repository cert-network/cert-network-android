package com.app.blockchain.certnetwork.common.base.mvvm.layer2ViewModel.bus.rx

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer


/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

class EventBusLiveData {
    private var livedata: MutableLiveData<Any> = MutableLiveData()

    fun post(obj: Any) {
        livedata.value = obj
    }

    fun newSubscribe(lifecycle: LifecycleOwner,
                     observer: Observer<Any>) {
        livedata = MutableLiveData()
        subscribe(lifecycle, observer)
    }

    fun subscribe(lifecycle: LifecycleOwner,
                  observer: Observer<Any>) {
        livedata.observe(lifecycle, observer)

    }


}
