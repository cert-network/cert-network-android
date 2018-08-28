package com.app.blockchain.certnetwork

import android.arch.lifecycle.Observer
import android.support.multidex.MultiDexApplication
import com.app.blockchain.certnetwork.appcommon.base.repository.base.database.hawk.HawkStorage
import com.app.blockchain.certnetwork.appcommon.base.repository.base.database.realm.CertDatabase
import com.app.blockchain.certnetwork.common.base.mvvm.layer2ViewModel.bus.rx.EventBusApplication
import com.orhanobut.hawk.Hawk


/**
 * Created by「 The Khaeng 」on 28 Aug 2017 :)
 */

class MainApplication : MultiDexApplication() {

    override
    fun onCreate() {
        super.onCreate()
        setupDatabase()
        EventBusApplication.subscribe(observer)
    }


    private fun setupDatabase() {
        Hawk.init(this)
                .setStorage(
                        HawkStorage(
                                this,
                                "227f75cbc25d740bc03abce42783616356031281" /* security_hawk */))
                .build()
        CertDatabase.initDatabase(this)
    }


    private val observer: Observer<Any>
        = Observer { event ->
    }
}
