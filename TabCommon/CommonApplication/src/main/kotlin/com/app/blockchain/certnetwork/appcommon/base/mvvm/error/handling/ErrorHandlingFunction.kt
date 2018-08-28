package com.app.blockchain.certnetwork.appcommon.base.mvvm.error.handling

import android.arch.core.util.Function
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResource
import java.lang.ref.WeakReference

/**
 * Created by「 The Khaeng 」on 30 Mar 2018 :)
 */
class ErrorHandlingFunction<X>(errorHandling: ErrorHandlingInterface) : Function<AppResource<X>, AppResource<X>> {

    var error: WeakReference<ErrorHandlingInterface>? = null


    init {
        this.error = WeakReference(errorHandling)
    }

    override
    fun apply(input: AppResource<X>?): AppResource<X>? {
        return input
//        return when {
//            input?.exception is NoInternetConnectionException -> {
//                error?.get()?.showNoInternetConnectionSnackbar()
//                AppResource.consume(input.isFetched)
//            }
//            input?.exception is SocketTimeoutException -> {
//                error?.get()?.showServiceUnavailableSnackbar()
//                AppResource.consume(input.isFetched)
//            }
//            else -> input
//        }
    }
}