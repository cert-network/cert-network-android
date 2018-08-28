package com.app.blockchain.certnetwork.common.base.mvvm.layer3Repository.network.interceptor


import com.lib.nextwork.exception.StatusCodeException

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */
class UnAuthorizedException(code: Int)
    : StatusCodeException(code, "for Unauthorized requests, when a request requires authentication " + "but it isn't provided")
