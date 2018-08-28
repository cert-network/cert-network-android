package com.app.blockchain.certnetwork.appcommon.base.repository.base.network

import com.lib.nextwork.engine.model.NextworkResult

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */
abstract class BaseResult<T>(
        var statusCode: Int? = -1,
        var message: String? = "",
        var error: String? = "",
        var data: T? = null
) : NextworkResult() {

    fun hasData(): Boolean = data != null

    fun isSuccess(): Boolean = 200 == statusCode

}
