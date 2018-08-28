package com.app.blockchain.certnetwork.repo.network.api

import com.app.blockchain.certnetwork.appcommon.base.repository.base.DefaultApiService

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */


class CertApiService private constructor()
    : DefaultApiService<CertApi>(CertApi::class.java) {

    companion object {
        val newInstance
            get() = CertApiService()
    }
}