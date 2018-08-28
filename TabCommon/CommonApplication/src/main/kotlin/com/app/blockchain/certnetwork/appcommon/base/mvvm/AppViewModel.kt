package com.app.blockchain.certnetwork.appcommon.base.mvvm

import com.app.blockchain.certnetwork.appcommon.module.AppRateLimiter
import com.app.blockchain.certnetwork.common.base.mvvm.layer2ViewModel.BaseViewModel


abstract class AppViewModel : BaseViewModel(){

    fun clearAllRateLimiter() {
        AppRateLimiter.INSTANCE.clearAll()
    }

}
