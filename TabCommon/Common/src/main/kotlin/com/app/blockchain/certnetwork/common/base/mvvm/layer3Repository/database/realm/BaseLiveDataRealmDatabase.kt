package com.app.blockchain.certnetwork.common.base.mvvm.layer3Repository.database.realm

import android.arch.lifecycle.LiveData
import com.lib.nextwork.engine.AbsentLiveData

/**
 * Created by「 The Khaeng 」on 11 Oct 2017 :)
 */


abstract class BaseLiveDataRealmDatabase : BaseRealmDatabase() {

    fun <T> nullLiveData(): LiveData<T> = AbsentLiveData.create()


}