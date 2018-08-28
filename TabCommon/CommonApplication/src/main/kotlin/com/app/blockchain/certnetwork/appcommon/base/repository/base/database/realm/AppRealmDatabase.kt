package com.app.blockchain.certnetwork.appcommon.base.repository.base.database.realm

import android.util.Log
import com.app.blockchain.certnetwork.common.base.mvvm.layer3Repository.database.realm.BaseLiveDataRealmDatabase
import com.lib.realm.database.deleteAll
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.RealmModule

/**
 * Created by「 The Khaeng 」on 28 Jun 2018 :)
 */
open class AppRealmDatabase : BaseLiveDataRealmDatabase() {

    fun deleteAllDatabase(): Boolean {
        return deleteAllDatabase(YourDatabaseModule::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> deleteAllDatabase(cls: Class<T>): Boolean {

        val annotation = cls.annotations
                .firstOrNull { it.annotationClass.java.name == RealmModule::class.java.name }

        if (annotation != null) {
            Log.i("RealmConfigStore", "Got annotation in module $annotation")
            val moduleAnnotation = annotation as RealmModule
            moduleAnnotation.classes
                    .filter { clazz -> clazz.java.interfaces.contains(RealmModel::class.java) }
                    .forEach { clazz -> (clazz.java as Class<RealmModel>).newInstance().deleteAll() }
            moduleAnnotation.classes
                    .filter { clazz -> clazz.java.superclass == RealmObject::class.java }
                    .forEach { clazz -> (clazz.java as Class<RealmObject>).newInstance().deleteAll() }
            return true
        }
        return false
    }
}