package com.app.blockchain.certnetwork.appcommon.base.repository.base.network.checker

import android.os.Build
import com.lib.nextwork.engine.model.ResultChecker
import com.lib.nextwork.exception.StatusCodeException
import com.app.blockchain.certnetwork.appcommon.base.repository.base.network.BaseResult
import com.app.blockchain.certnetwork.appcommon.base.repository.base.network.exception.EmptyDataListException
import com.app.blockchain.certnetwork.appcommon.base.repository.base.network.exception.NullDataException
import com.app.blockchain.certnetwork.common.base.mvvm.layer3Repository.network.interceptor.UnAuthorizedException
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.functions.Function
import retrofit2.Response

/**
 * Created by「 The Khaeng 」on 28 Mar 2018 :)
 */
const val UNAUTHORIZED_CODE = 401

fun Throwable?.isNoDataException(): Boolean {
    return this is NullDataException || this is EmptyDataListException
}

fun Throwable?.isNoDataMessageException(): String {
    if (this.isNoDataException()) {
        return this?.message ?: ""
    }
    return ""
}

open class AppResponseChecker<T : BaseResult<*>> : ResultChecker<T>() {

    override
    fun apply(response: Single<Response<T>>): Single<Response<T>> {
        return super.apply(response)
                .onErrorResumeNext(checkExceptionSingle())
                .flatMap {
                    when {
                        it.body()?.data == null -> Single.error(NullDataException(it.body()?.error).apply {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                addSuppressed(Exception(it.body()?.javaClass?.simpleName + ": body is null."))
                            }
                        })
                        else -> Single.just(it)
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