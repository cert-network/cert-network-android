package com.app.blockchain.certnetwork.common.base.mvvm.layer2ViewModel.bus.rx

import android.arch.lifecycle.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by「 The Khaeng 」on 02 Apr 2018 :)
 */
object EventBusApplication {
    private val subject: EventBusSubject = EventBusSubject()

    fun post(obj: Any) {
        subject.post(obj)
    }

    fun newSubscribe(observer: Observer<Any>) {
        subject.clearCache()
        subscribe(observer)
    }

    fun subscribe(observer: Observer<Any>) {
        this.subject.observe().subscribe(object : io.reactivex.Observer<Any> {
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

        })
    }

}