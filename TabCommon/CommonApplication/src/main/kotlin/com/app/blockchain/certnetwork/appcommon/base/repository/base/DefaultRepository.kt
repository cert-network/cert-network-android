package com.app.blockchain.certnetwork.appcommon.base.repository.base

import android.content.Context
import com.app.blockchain.certnetwork.appcommon.base.repository.base.database.StringDatabase
import com.app.blockchain.certnetwork.appcommon.base.repository.base.database.realm.CertDatabase
import com.app.blockchain.certnetwork.appcommon.module.AppRateLimiter
import com.lib.nextwork.engine.AppExecutors

/**
 * Created by「 The Khaeng 」on 01 Feb 2018 :)
 */
open class DefaultRepository(
        var appExecutors: AppExecutors,
        val database: CertDatabase = CertDatabase.INSTANCE,
        val databaseString: StringDatabase = StringDatabase.INSTANCE,
        var rateLimiter: AppRateLimiter = AppRateLimiter.INSTANCE
) {

    companion object {
        @Volatile
        private var INSTANCE: DefaultRepository? = null

        fun getInstance(appExecutors: AppExecutors = AppExecutors.getInstance()): DefaultRepository =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: DefaultRepository(appExecutors).also { INSTANCE = it }
                }
    }



    fun clearAllRateLimiter() {
        rateLimiter.clearAll()
    }

    fun initDatabase(context: Context): Boolean {
        return CertDatabase.initDatabase(context)
    }

    fun clearRateLimiter(key: String) {
        rateLimiter.clear(key)
    }

}