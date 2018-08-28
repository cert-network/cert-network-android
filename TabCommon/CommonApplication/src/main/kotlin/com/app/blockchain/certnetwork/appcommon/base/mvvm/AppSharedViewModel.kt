package com.app.blockchain.certnetwork.appcommon.base.mvvm

import com.app.blockchain.certnetwork.appcommon.module.AppRateLimiter
import com.app.blockchain.certnetwork.common.base.mvvm.layer2ViewModel.BaseSharedViewModel


abstract class AppSharedViewModel : BaseSharedViewModel() {

    fun clearAllRateLimiter() {
        AppRateLimiter.INSTANCE.clearAll()
    }


}
