package com.app.blockchain.certnetwork.appcommon.base.repository.base.model

import com.lib.nextwork.engine.model.NetworkStatus
import com.lib.nextwork.engine.model.NextworkResource
import com.lib.nextwork.engine.model.Status


/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */
class AppResource<T> private constructor(
        @Status status: Int,
        data: T?,
        exception: Throwable?,
        payload: Any?,
        isFetched: Boolean,
        isCached: Boolean = false)
    : NextworkResource<T>(status, data, exception, payload, isFetched, isCached) {

    companion object {
        fun <T> success(data: T, payload: Any?, isCached: Boolean, isFetched: Boolean): AppResource<T> {
            return AppResource(NetworkStatus.SUCCESS,
                    data,
                    null,
                    payload,
                    isFetched,
                    isCached)
        }

        fun <T> loadingFromNetwork(data: T, payload: Any?, isFetched: Boolean): AppResource<T> {
            return AppResource(NetworkStatus.LOADING_FROM_NETWORK,
                    data,
                    null,
                    payload,
                    isFetched)
        }

        fun <T> loadingFromDatabase(payload: Any?): AppResource<T> {
            return AppResource(NetworkStatus.LOADING_FROM_DATABASE,
                    null,
                    null,
                    payload,
                    false,
                    true)
        }

        fun <T> error(error: Throwable?,
                      data: T,
                      payload: Any?,
                      isFetched: Boolean): AppResource<T> {
            return AppResource(NetworkStatus.ERROR,
                    data,
                    error,
                    payload,
                    isFetched)
        }

        fun <T> consume(isFetched: Boolean): AppResource<T> {
            return AppResource(NetworkStatus.CONSUME,
                    null,
                    null,
                    null,
                    isFetched)
        }

    }
}