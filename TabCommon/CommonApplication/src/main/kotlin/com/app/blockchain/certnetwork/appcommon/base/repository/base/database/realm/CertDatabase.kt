package com.app.blockchain.certnetwork.appcommon.base.repository.base.database.realm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import com.app.blockchain.certnetwork.appcommon.base.repository.base.database.realm.model.DefaultRealmObject
import com.app.blockchain.certnetwork.common.extension.asLiveData
import com.google.gson.Gson
import com.lib.nextwork.engine.model.NextworkModel
import com.lib.realm.database.queryFirst
import com.lib.realm.database.queryFirstAsSingle
import com.lib.realm.database.save
import com.lib.realm.database.saveAsSingle
import io.reactivex.Single


class CertDatabase private constructor() : AppRealmDatabase() {

    private object Holder {
        val INSTANCE = CertDatabase()

    }

    companion object {
        const val DATABASE_VERSION = 1
        const val ACCOUNT_ID = "account_id"
        const val CERT_ID = "cert_id"

        val INSTANCE: CertDatabase by lazy { Holder.INSTANCE }

        fun initDatabase(context: Context): Boolean {
            return RealmConfig.init(
                    context,
                    YourDatabaseModule(),
                    DATABASE_VERSION)
        }
    }




    /* =========================== Default Model ================================================ */
    fun <T : NextworkModel> saveModel(item: T) =
            DefaultRealmObject(
                    item.databaseId,
                    Gson().toJson(item))
                    .save()

    fun <T : NextworkModel> saveModel(prefixId: String, item: T) =
            DefaultRealmObject(
                    prefixId + item.databaseId,
                    Gson().toJson(item))
                    .save()


    fun <T : NextworkModel> saveModelAsSingle(item: T): Single<T> =
            DefaultRealmObject(
                    item.databaseId,
                    Gson().toJson(item))
                    .saveAsSingle()
                    .map { item }

    fun <T : NextworkModel> loadModelAsLiveData(id: String, clazz: Class<T>): LiveData<T> = Transformations.map(
            DefaultRealmObject()
                    .queryFirstAsSingle { equalTo(DefaultRealmObject.FIELD_ID_NAME, id) }
                    .asLiveData())
    { realmObject: DefaultRealmObject? ->
        if (realmObject?.isValid == true) {
            Gson().fromJson(realmObject.json, clazz)
        } else {
            null
        }
    }

    fun <T : NextworkModel> loadModelAsSingle(id: String, clazz: Class<T>): Single<T> = DefaultRealmObject()
            .queryFirstAsSingle { equalTo(DefaultRealmObject.FIELD_ID_NAME, id) }
            .map { realmObject: DefaultRealmObject? ->
                if (realmObject?.isValid == true) {
                    Gson().fromJson(realmObject.json, clazz)
                } else {
                    null
                }
            }


    fun <T : NextworkModel> loadModel(id: String, clazz: Class<T>): T? =
            Gson().fromJson(
                    DefaultRealmObject()
                            .queryFirst { equalTo(DefaultRealmObject.FIELD_ID_NAME, id) }?.json,
                    clazz)




}
