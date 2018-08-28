package com.app.blockchain.certnetwork.appcommon.base.repository.base.model

import com.lib.nextwork.engine.model.NextworkResponse

import retrofit2.Response

/**
 * Default class used by API responses.
 *
 * @param <T>
</T> */
class AppResponse<T> : NextworkResponse<T> {

    constructor(code: Int, body: T?, error: Throwable?) : super(code, body, error) {}

    constructor(error: Throwable) : super(error) {}

    constructor(response: Response<T>) : super(response) {
    }

    constructor(response: T) : super(response) {
    }
}
