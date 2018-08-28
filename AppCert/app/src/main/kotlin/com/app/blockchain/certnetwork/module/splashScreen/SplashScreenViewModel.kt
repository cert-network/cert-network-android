package com.app.blockchain.certnetwork.module.splashScreen

import com.app.blockchain.certnetwork.common.base.mvvm.layer2ViewModel.BaseViewModel
import com.app.blockchain.certnetwork.repo.CertRepository
import com.app.blockchain.certnetwork.repo.model.Account

/**
 * Created by「 The Khaeng 」on 21 Oct 2017 :)
 */

class SplashScreenViewModel(
        var repo: CertRepository = CertRepository.getInstance()
) : BaseViewModel() {

    fun loadAccount(): Account? = repo.loadAccount()
}
