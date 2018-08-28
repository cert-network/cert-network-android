package com.app.blockchain.certnetwork.common.extension

import android.os.SystemClock
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by「 The Khaeng 」on 08 Apr 2018 :)
 */
const val DEFAULT_DELAY = 1000L
const val DEFAULT_BOUNCE = 200L

val mapTimestamp: MutableMap<String, Long> = mutableMapOf()
fun bouncedAction(key: String, minimumIntervalMillis: Long = DEFAULT_BOUNCE, action: () -> Unit) {
    val previousClickTimestamp = mapTimestamp[key]
    val currentTimestamp = SystemClock.uptimeMillis()
    mapTimestamp[key] = currentTimestamp
    if (previousClickTimestamp == null || currentTimestamp - previousClickTimestamp > minimumIntervalMillis) {
        action()
    }
}


fun delay(action: () -> Unit, delay: Long = DEFAULT_DELAY) {
    Observable.empty<Any>()
            .delay(delay, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { action() }
            .subscribe()
}

fun doOnBackground(action: () -> Unit) {
    Observable.empty<Any>()
            .subscribeOn(Schedulers.io())
            .doOnComplete { action() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
}
