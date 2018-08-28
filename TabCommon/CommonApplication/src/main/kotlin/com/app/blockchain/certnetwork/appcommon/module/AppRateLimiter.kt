package com.app.blockchain.certnetwork.appcommon.module

import com.lib.nextwork.engine.RateLimiter
import java.util.concurrent.TimeUnit

/**
 * Created by「 The Khaeng 」on 09 Apr 2018 :)
 */

class AppRateLimiter(
        timeout: Int,
        timeUnit: TimeUnit
) : RateLimiter<String>(timeout, timeUnit) {

    private var firstCall: Long = 0L

    private object Holder {
        val INSTANCE = AppRateLimiter(10, TimeUnit.MINUTES)
    }

    companion object {
        val INSTANCE: AppRateLimiter by lazy { Holder.INSTANCE }
    }

    override
    fun clearAll() {
        firstCall = 0L
        for((key,_) in timestamps){
            timestamps?.put(key, 0L)
        }
    }

    override
    fun shouldFetch(key: String?): Boolean {
        firstFetch()
        return super.shouldFetch(key)
    }

    override
    fun onNullLastFetched(key: String?, timestamps: Map<String, Long>?): Boolean {
        return firstCall == 0L
    }


    @Synchronized
    fun firstFetch() {
        if (firstCall == 0L) {
            firstCall = now()
        }
    }


}