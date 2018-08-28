package com.app.blockchain.certnetwork.appcommon.base.repository.base

import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResponse

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */

@Suppress("UNCHECKED_CAST")
class ConvertResponseToAppResponse<FROM : Response<*>, TO : AppResponse<*>> : SingleTransformer<FROM, TO> {

    override
    fun apply(upstream: Single<FROM>): SingleSource<TO> {
        return upstream
                .subscribeOn(Schedulers.io())
                .map { from -> AppResponse(from) as TO }
                .onErrorResumeNext { throwable -> Single.just<AppResponse<*>>(AppResponse<Any>(throwable)) as SingleSource<TO> }
                .observeOn(AndroidSchedulers.mainThread())
    }
}



