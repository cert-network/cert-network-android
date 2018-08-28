package com.app.blockchain.certnetwork.appcommon.base.repository.base.network


import com.lib.nextwork.engine.NextworkApiCreator
import okhttp3.OkHttpClient




/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

abstract class BaseApiCreator<T>(apiClass: Class<T>) : NextworkApiCreator<T>(apiClass) {


    override
    fun getClient(): OkHttpClient = DefaultClient.okHttpClient

}
