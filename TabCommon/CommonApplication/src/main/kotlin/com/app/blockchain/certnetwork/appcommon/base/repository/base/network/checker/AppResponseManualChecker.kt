package com.app.blockchain.certnetwork.appcommon.base.repository.base.network.checker

import com.lib.nextwork.exception.StatusCodeException
import com.app.blockchain.certnetwork.appcommon.base.repository.base.network.BaseResult
import com.app.blockchain.certnetwork.common.base.mvvm.layer3Repository.network.interceptor.UnAuthorizedException
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.functions.Function
import retrofit2.Response

/**
 * Created by「 The Khaeng 」on 28 Mar 2018 :)
 */

open class AppResponseManualChecker<T : BaseResult<*>>(val callback: (response: T?) -> Exception?) : AppResponseChecker<T>() {

    override
    fun apply(response: Single<Response<T>>): Single<Response<T>> {
        return super.apply(response)
                .onErrorResumeNext(checkExceptionSingle())
                .flatMap {
                    val exception = callback.invoke(it.body())
                    if (exception != null) {
                        Single.error(exception)
                    } else {
                        Single.just(it)
                    }

                }
    }

    private fun checkExceptionSingle(): Function<Throwable, SingleSource<Response<T>>> {
        return Function { throwable ->
            if (throwable is StatusCodeException) {
                if (throwable.statusCode == UNAUTHORIZED_CODE) {
                    return@Function Single.error(UnAuthorizedException(UNAUTHORIZED_CODE))
                }
            }
            return@Function Single.error(throwable)
        }
    }

}