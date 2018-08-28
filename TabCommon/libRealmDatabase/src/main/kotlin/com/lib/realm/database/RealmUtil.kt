package com.lib.realm.database

import android.os.HandlerThread
import android.os.Looper
import android.os.Process
import io.realm.Realm

/**
 * Created by「 The Khaeng 」on 27 Jun 2018 :)
 */

internal fun getLooper(): Looper? {
    return if (Looper.myLooper() == null) {
        val backgroundThread = HandlerThread(REALM_THREAD_NAME,
                Process.THREAD_PRIORITY_BACKGROUND)
        backgroundThread.start()
        backgroundThread.looper
    } else {
        Looper.myLooper()
    }
}

const val REALM_THREAD_NAME = "Scheduler-Realm-BackgroundThread"

fun isRealmThread() = Thread.currentThread().name == REALM_THREAD_NAME

fun Realm.closeIfAvailable(){
    if (!this.isClosed && !this.isInTransaction) {
        this.close()
    }
}