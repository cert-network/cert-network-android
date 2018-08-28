package com.app.blockchain.certnetwork.appcommon.base.repository.base

import com.app.blockchain.certnetwork.appcommon.URL
import com.app.blockchain.certnetwork.appcommon.base.repository.base.database.realm.CertDatabase
import com.app.blockchain.certnetwork.appcommon.base.repository.base.network.BaseApiCreator

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */


abstract class DefaultApiService<T>(
        apiClass: Class<T>,
        val database: CertDatabase = CertDatabase.INSTANCE
) : BaseApiCreator<T>(apiClass) {


    override
    fun getBaseUrl(): String {
        return URL.BASE
    }

//    override
//    fun getHeader(headerMap: MutableMap<String, String>): MutableMap<String, String> {
//        return headerMap
//    }



}
