package com.app.blockchain.certnetwork.common.base.delegate

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */
class RxDelegation {
    private val disposables = CompositeDisposable()

    fun addDisposable(d: Disposable) {
        disposables.add(d)
    }

    fun clearAllDisposables() {
        disposables.clear()
    }
}