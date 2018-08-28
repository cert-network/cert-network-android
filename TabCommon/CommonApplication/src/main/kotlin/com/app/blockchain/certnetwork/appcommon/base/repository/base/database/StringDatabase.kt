package com.app.blockchain.certnetwork.appcommon.base.repository.base.database

import android.content.Context
import com.app.blockchain.certnetwork.common.extension.HASH
import com.orhanobut.hawk.Hawk


class StringDatabase private constructor() {

    private object Holder {
        val INSTANCE = StringDatabase()
    }

    companion object {
        var TOKEN: String = "3C469E9D6C5875D37A43F353D4F88E61FCF812C66EEE3457465A40B0DA4153E0" // sha256:token
        val INSTANCE: StringDatabase by lazy { Holder.INSTANCE }
    }

    private var deviceId: String = ""

    fun initDatabase(context: Context, deviceId: String) {
        this.deviceId = deviceId
    }


    fun clearAllData(): Boolean {
        return Hawk.deleteAll()
    }


    fun saveToken(token: String) {
        Hawk.put(encryptKey("$deviceId:$TOKEN"), token)
    }

    fun loadToken(): String?
            = Hawk.get<String>(encryptKey("$deviceId:$TOKEN"))


    private fun encryptKey(key: String): String = HASH.sha256(key)

}
