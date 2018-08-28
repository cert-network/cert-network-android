package com.app.blockchain.certnetwork.common.base.mvvm.layer3Repository.database.realm

import java.util.*

/**
 * Created by「 The Khaeng 」on 11 Oct 2017 :)
 */
abstract class BaseRealmDatabase {


    fun createId(): String = UUID.randomUUID().toString()

}