package com.app.blockchain.certnetwork.common.base.mvvm.layer2ViewModel


import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.app.blockchain.certnetwork.common.base.mvvm.layer2ViewModel.bus.rx.EventBusLiveData


/**
 * Created by「 The Khaeng 」on 23 Aug 2017 :)
 */

abstract class BaseSharedViewModel : BaseViewModel() {
    companion object {
        lateinit var busViewModel: EventBusLiveData
            private set
    }

    init {
        busViewModel = EventBusLiveData()
    }

    fun postEvent(obj: Any) {
        busViewModel.post(obj)
    }

    fun subscribeNewBus(activity: FragmentActivity,
                        observer: Observer<Any>) {
        busViewModel.newSubscribe(activity, observer)

    }

    fun subscribeBus(fragment: FragmentActivity,
                     observer: Observer<Any>) {
        busViewModel.subscribe(fragment, observer)
    }

    fun subscribeBus(fragment: Fragment,
                     observer: Observer<Any>) {
        busViewModel.subscribe(fragment, observer)
    }

}
