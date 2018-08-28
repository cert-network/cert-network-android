package com.lib.realm.database

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.realm.*

/**
 * Created by「 The Khaeng 」on 27 Jun 2018 :)
 */

fun <T : RealmModel> T.saveAsSingle(): Single<T> =
        Single.create { singleEmitter ->
            getRealmInstance().transaction { realm ->
                if (isAutoIncrementPK()) {
                    initPk(realm)
                }
                if (this.hasPrimaryKey(realm)) realm.copyToRealmOrUpdate(this) else realm.copyToRealm(this)
                singleEmitter.onSuccess(this)
            }
            singleEmitter.setDisposable(Disposables.fromRunnable { getRealmInstance().closeIfAvailable() })
        }

fun <T : RealmModel> T.queryFirstAsSingle(query: Query<T>) = singleQuery(query = query).map { it.firstOrNull() }

fun <T : RealmModel> T.queryLastAsSingle(query: Query<T>) = singleQuery(query = query).map { it.lastOrNull() }


inline fun <reified D : RealmModel, T : Collection<D>> T.saveAllAsSingle(): Single<T> =
        Single.create { singleEmitter ->
            if (size > 0) {
                getRealmInstance().transaction { realm ->
                    if (first().isAutoIncrementPK()) {
                        initPk(realm)
                    }
                    forEach { if (it.hasPrimaryKey(realm)) realm.copyToRealmOrUpdate(it) else realm.copyToRealm(it) }
                    singleEmitter.onSuccess(this)
                }
            }
            singleEmitter.setDisposable(Disposables.fromRunnable { getRealmInstance().closeIfAvailable() })
        }


/**
 * Query for all items and listen to changes returning an Single.
 */
fun <T : RealmModel> T.queryAllAsSingle() = singleQuery()

inline fun <reified T : RealmModel> queryAllAsSingle() = singleQuery<T>()

/**
 * Queries for entities in database asynchronously, and observe changes returning an Single.
 */
fun <T : RealmModel> T.queryAsSingle(query: Query<T>) = singleQuery(query = query)


inline fun <reified T : RealmModel> queryAsSingle(noinline query: Query<T>) = singleQuery(query = query)

/**
 * Query for sorted entities and observe changes returning a Single.
 */
fun <T : RealmModel> T.querySortedAsSingle(fieldName: List<String>, order: List<Sort>, query: Query<T>? = null) = singleQuery(fieldName, order, query)

inline fun <reified T : RealmModel> querySortedAsSingle(fieldName: List<String>, order: List<Sort>, noinline query: Query<T>? = null) = singleQuery(fieldName, order, query)

/**
 * Query for sorted entities and observe changes returning a Single.
 */
fun <T : RealmModel> T.querySortedAsSingle(fieldName: String, order: Sort, query: Query<T>? = null) = singleQuery(listOf(fieldName), listOf(order), query)

inline fun <reified T : RealmModel> querySortedAsSingle(fieldName: String, order: Sort, noinline query: Query<T>? = null) = singleQuery(listOf(fieldName), listOf(order), query)


fun <T : RealmModel> T.deleteAllAsSingle(): Single<List<T>> {
    val realm = getRealmInstance()
    return performDeleteSingle { realm.where(this.javaClass).findAll() }
}

fun <T : RealmModel> T.deleteAsSingle(myQuery: Query<T>): Single<List<T>> {
    val realm = getRealmInstance()
    return performDeleteSingle { realm.where(this.javaClass).withQuery(myQuery).findAll() }
}

internal fun <T : RealmModel> T.performDeleteSingle(action: () -> RealmResults<T>): Single<List<T>> {
    val looper = getLooper()
    return Single.create<List<T>> { emitter ->
        var realmResult: RealmResults<T>? = null
        val realm = getRealmInstance()
        realm.transaction {
            realmResult = action()
            val copy = it.copyFromRealm(realmResult)
            realmResult?.deleteAllFromRealm()
            emitter.onSuccess(copy)
        }


        emitter.setDisposable(Disposables.fromAction {
            realmResult?.removeAllChangeListeners()
            realm.close()
            if (isRealmThread()) {
                looper?.thread?.interrupt()
            }
        })
    }.subscribeOn(AndroidSchedulers.from(looper))
            .unsubscribeOn(AndroidSchedulers.from(looper))
}

/**
 * INTERNAL FUNCTIONS
 */
@PublishedApi
internal inline fun <reified T : RealmModel> singleQuery(fieldName: List<String>? = null, order: List<Sort>? = null, noinline query: Query<T>? = null) = performSingleQuery(fieldName, order, query, T::class.java)


private fun <T : RealmModel> T.singleQuery(fieldName: List<String>? = null, order: List<Sort>? = null, query: Query<T>? = null) = performSingleQuery(fieldName, order, query, this.javaClass)

@PublishedApi
internal fun <T : RealmModel> performSingleQuery(fieldName: List<String>? = null, order: List<Sort>? = null, query: Query<T>? = null, javaClass: Class<T>): Single<List<T>> {
    val looper = getLooper()
    return Single.create<List<T>> { emitter ->

        val realm = RealmConfigStore
                .fetchConfiguration(javaClass)
                ?.realm() ?: Realm.getDefaultInstance()
        val realmQuery: RealmQuery<T> = realm.where(javaClass)
        query?.invoke(realmQuery)

        val result = if (fieldName != null && order != null) {
            realmQuery.sort(fieldName.toTypedArray(), order.toTypedArray()).findAllAsync()
        } else {
            realmQuery.findAllAsync()
        }

        result.addChangeListener { it ->
            emitter.onSuccess(realm.copyFromRealm(it))
        }

        emitter.setDisposable(Disposables.fromAction {
            result.removeAllChangeListeners()
            realm.close()
            if (isRealmThread()) {
                looper?.thread?.interrupt()
            }
        })
    }.subscribeOn(AndroidSchedulers.from(looper))
            .unsubscribeOn(AndroidSchedulers.from(looper))
}