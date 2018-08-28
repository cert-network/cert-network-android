package com.app.blockchain.certnetwork.appcommon.base.repository.base.network

import com.lib.nextwork.engine.model.NextworkResult

/**
 * Created by A Little Boy @Nextzy on 30/4/2018 AD.
 */
abstract class BaseResultNoData(
        var statusCode: Int? = -1,
        var message: String? = "",
        var error: String? = ""
) : NextworkResult() {

    fun isSuccess(): Boolean = 200 == statusCode
}