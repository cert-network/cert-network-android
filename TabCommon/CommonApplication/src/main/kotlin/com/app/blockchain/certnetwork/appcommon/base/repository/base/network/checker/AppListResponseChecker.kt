package com.app.blockchain.certnetwork.appcommon.base.repository.base.network.checker

import android.os.Build
import com.app.blockchain.certnetwork.appcommon.base.repository.base.network.BaseResult
import com.app.blockchain.certnetwork.appcommon.base.repository.base.network.exception.EmptyDataListException
import io.reactivex.Single
import retrofit2.Response

/**
 * Created by「 The Khaeng 」on 28 Mar 2018 :)
 */

open class AppListResponseChecker<T : BaseResult<*>> : AppResponseChecker<T>() {

    override
    fun apply(response: Single<Response<T>>): Single<Response<T>> {
        return super.apply(response)
                .flatMap {
                    val data: Any? = it.body()?.data
                    if (data is Collection<*> && data.isEmpty()) {
                        Single.error(EmptyDataListException(it.body()?.error).apply {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                addSuppressed(Exception(data.javaClass.simpleName + ": data list is empty."))
                            }
                        })
                    } else {
                        Single.just(it)
                    }
                }


    }

}