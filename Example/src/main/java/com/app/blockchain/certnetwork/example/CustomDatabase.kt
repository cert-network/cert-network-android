package com.app.blockchain.certnetwork.example

import android.content.Context
import com.app.blockchain.certnetwork.common.base.mvvm.layer3Repository.database.realm.BaseRealmDatabase
import com.app.blockchain.certnetwork.example.database.ExampleDatabaseModule
import com.app.blockchain.certnetwork.example.database.ExampleRealmObject
import com.google.gson.Gson
import com.lib.realm.database.*
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.*

class CustomDatabase : BaseRealmDatabase() {

    private object Holder {
        val INSTANCE = CustomDatabase()
    }

    private val random = Random()

    companion object {

        val instance: CustomDatabase by lazy { Holder.INSTANCE }
    }


    fun initDatabase(context: Context) {
        Realm.init(context)
        RealmConfigStore.initModule(
                ExampleDatabaseModule::class.java,
                RealmConfiguration.Builder()
                        .name("test.realm")
                        .modules(ExampleDatabaseModule())
                        .schemaVersion(1)
                        .build())
    }


    fun saveMockObject(mode: Int, obj: MockObject): Single<MockObject> {
        if (obj.id == null) obj.id = randInt()
        val json = Gson().toJson(obj)
        return ExampleRealmObject(
                obj.id,
                obj.javaClass.simpleName,
                json).saveAsSingle()
                .map { obj }
    }

    fun findMockObject(mode: Int, id: String): Single<MockObject> {
        return ExampleRealmObject()
                .queryFirstAsSingle { equalTo(ExampleRealmObject.KEY_ID, id) }
                .map { defaultRealmObject: ExampleRealmObject ->
                    Gson().fromJson(
                            defaultRealmObject.json, MockObject::class.java)

                }
    }

    fun findAllMockObject(mode: Int): Single<MutableList<MockObject>> {
        return ExampleRealmObject()
                .queryAllAsSingle()
                .map { defaultRealmObject ->
                    val list = defaultRealmObject.indices.mapTo(ArrayList<MockObject>()) {
                        Gson().fromJson(
                                defaultRealmObject[it].json, MockObject::class.java)
                    }
                    list
                }
    }



    fun deleteMockObject(mode: Int, id: String): Single<List<MockObject>> {
        return ExampleRealmObject().deleteAsSingle { equalTo(ExampleRealmObject.KEY_ID, id) }
                .map { defaultRealmObjects ->
                    defaultRealmObjects.map {
                        Gson().fromJson(
                                it.json, MockObject::class.java)
                    }
                }
    }

    fun deleteAllMockObject(mode: Int): Single<MutableList<MockObject>> {
        return ExampleRealmObject()
                .deleteAllAsSingle()
                .map { defaultRealmObject ->
                    val list = defaultRealmObject.indices.mapTo(ArrayList<MockObject>()) {
                        Gson().fromJson(
                                defaultRealmObject[it].json, MockObject::class.java)
                    }
                    list
                }
    }

    private fun randInt(): String {
        return random.nextInt(100).toString()
    }
}