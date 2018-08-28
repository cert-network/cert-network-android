package com.app.blockchain.certnetwork.appcommon.base.repository.base

import com.lib.nextwork.engine.CacheBoundResource
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResource

abstract class DefaultCacheBoundResource<ResultType>
    : CacheBoundResource<ResultType, AppResource<ResultType>>(creator()) {
}


