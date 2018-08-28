package com.app.blockchain.certnetwork.appcommon.base.repository.base

import com.lib.nextwork.engine.AppExecutors
import com.lib.nextwork.engine.NextworkCacheBoundResource
import com.lib.nextwork.engine.NextworkResourceCreator
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResource
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResponse

abstract class DefaultNetworkCacheBoundResource<ResultType, RequestType>(
        appExecutor: AppExecutors?,
        resourceCreator: NextworkResourceCreator<ResultType, AppResource<ResultType>> = creator()
)
    : NextworkCacheBoundResource<ResultType, RequestType, AppResponse<RequestType>, AppResource<ResultType>>(
        appExecutor,
        resourceCreator)


fun <ResultType> creator(): NextworkResourceCreator<ResultType, AppResource<ResultType>> =
        object : NextworkResourceCreator<ResultType, AppResource<ResultType>> {
            override
            fun loadingFromDatabase(payload: Any?): AppResource<ResultType> {
                return AppResource.loadingFromDatabase(payload)
            }

            override
            fun loadingFromNetwork(data: ResultType, payload: Any?, isFetch: Boolean): AppResource<ResultType> {
                return AppResource.loadingFromNetwork(data, payload, isFetch)
            }

            override
            fun success(newData: ResultType, payload: Any?, isCache: Boolean, isFetch: Boolean): AppResource<ResultType> {
                return AppResource.success(newData, payload, isCache, isFetch)
            }

            override
            fun error(error: Throwable?, oldData: ResultType, payload: Any?, isFetch: Boolean): AppResource<ResultType> {
                return AppResource.error(error, oldData, payload, isFetch)
            }
        }









