package com.app.blockchain.certnetwork.common.base.mvvm.layer3Repository.network.interceptor

import com.lib.nextwork.exception.StatusCodeException


/**
* Created by「 The Khaeng 」on 17 Oct 2017 :)
*/
class PermissionException(code: Int)
    : StatusCodeException(code, "for Forbidden requests, when a request may be valid but the user " + "doesn't have permissions to perform the action")
