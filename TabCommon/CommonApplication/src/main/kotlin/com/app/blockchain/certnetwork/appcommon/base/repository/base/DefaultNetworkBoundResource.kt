package com.app.blockchain.certnetwork.appcommon.base.repository.base

import com.lib.nextwork.engine.AppExecutors
import com.lib.nextwork.engine.NextworkBoundResource
import com.lib.nextwork.engine.NextworkResourceCreator
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResource
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResponse

abstract class DefaultNetworkBoundResource<ResultType, RequestType>(
        appExecutor: AppExecutors?,
        resourceCreator: NextworkResourceCreator<ResultType, AppResource<ResultType>> = creator()
) : NextworkBoundResource<ResultType, RequestType, AppResponse<RequestType>, AppResource<ResultType>>(
        appExecutor,
        resourceCreator)


