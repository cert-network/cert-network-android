package com.app.blockchain.certnetwork.appcommon.base.repository.base.database.realm

import android.content.Context
import com.lib.realm.database.RealmConfigStore
import io.realm.Realm
import io.realm.RealmConfiguration

object RealmConfig {

    private lateinit var config: RealmConfiguration


    fun init(context: Context,
             module: Any,
             version: Int): Boolean {
        Realm.init(context)

        config = RealmConfiguration.Builder()
                .name("8a4205457bcddbd2c14d4b5e7b392667ccbc6758")
                .modules(module)
//                .encryptionKey(realmKey)
                .schemaVersion(version.toLong())
                .build()

        RealmConfigStore
                .initModule(module::class.java, config)

        return true
    }



}
