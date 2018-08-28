package com.app.blockchain.certnetwork.common.extension

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import com.lib.nextwork.engine.AbsentLiveData
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

fun <T> T.asObjectLiveData(): LiveData<T> {
    return object : LiveData<T>() {
        init {
            value = this@asObjectLiveData
        }
    }
}

fun <T> Single<T>.asLiveData(): LiveData<T> {
    return object : LiveData<T>() {
        init {
            this@asLiveData.subscribe(object : SingleObserver<T?> {
                override
                fun onSuccess(t: T) {
                    value = t
                }


                override
                fun onSubscribe(d: Disposable) {
                }

                override
                fun onError(e: Throwable) {
                    if (e is NullPointerException) {
                       value = null
                    }
                    Timber.e("Single asLiveData: ", e)
                }

            })
        }
    }
}

fun <T> LiveData<Boolean>.fetchSwitchMap(func: Function<Boolean, LiveData<T>>): LiveData<T> {
    return Transformations.switchMap<Boolean, T>(
            this
    ) { forceFetch ->
        if (forceFetch == null) {
            AbsentLiveData.create<T>()
        } else {
            func.apply(forceFetch)
        }
    }
}

fun <A, B> zipLiveData(a: LiveData<A>, b: LiveData<B>): LiveData<Pair<A, B>> {
    return MediatorLiveData<Pair<A, B>>().apply {
        var lastA: A? = null
        var lastB: B? = null

        fun update() {
            val localLastA = lastA
            val localLastB = lastB
            if (localLastA != null && localLastB != null)
                this.value = Pair(localLastA, localLastB)
        }

        addSource(a) {
            lastA = it
            update()
        }
        addSource(b) {
            lastB = it
            update()
        }
    }
}

/**
 * This is merely an extension function for [zipLiveData].
 *
 * @see zipLiveData
 * @author Mitchell Skaggs
 */
fun <A, B> LiveData<A>.zip(b: LiveData<B>): LiveData<Pair<A, B>> = zipLiveData(this, b)

/**
 * This is an extension function that calls to [Transformations.map].
 *
 * @see Transformations.map
 * @author Mitchell Skaggs
 */
fun <A, B> LiveData<A>.map(function: (A) -> B): LiveData<B> = Transformations.map(this, function)

/**
 * This is an extension function that calls to [Transformations.switchMap].
 *
 * @see Transformations.switchMap
 * @author Mitchell Skaggs
 */
fun <A, B> LiveData<A>.switchMap(function: (A) -> LiveData<B>): LiveData<B> = Transformations.switchMap(this, function)
