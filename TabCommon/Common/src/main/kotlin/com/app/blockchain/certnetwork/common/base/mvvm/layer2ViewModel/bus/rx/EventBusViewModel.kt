package com.app.blockchain.certnetwork.common.base.mvvm.layer2ViewModel.bus.rx

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import io.reactivex.disposables.Disposable


/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

class EventBusViewModel : LifecycleObserver {
    private val subject: EventBusSubject = EventBusSubject()


    fun post(obj: Any) {
        subject.post(obj)
    }

    fun newSubscribe(lifecycle: LifecycleOwner,
                     observer: Observer<Any>) {
        subject.clearCache()
        subscribe(lifecycle, observer)
    }

    fun subscribe(fragment: LifecycleOwner,
                  observer: Observer<Any>) {
        this.subject.observe().subscribe(createObserver(observer))
    }

    private fun createObserver(observer: Observer<Any>): io.reactivex.Observer<Any> {
        return object : io.reactivex.Observer<Any> {
            override
            fun onComplete() {
            }

            override
            fun onSubscribe(d: Disposable) {
            }

            override
            fun onNext(o: Any) {
                observer.onChanged(o)
            }

            override
            fun onError(e: Throwable) {
                observer.onChanged(e)
            }

        }
    }


}
