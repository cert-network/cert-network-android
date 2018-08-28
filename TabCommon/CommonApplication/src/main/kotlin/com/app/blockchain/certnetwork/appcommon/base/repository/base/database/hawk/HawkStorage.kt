package com.app.blockchain.certnetwork.appcommon.base.repository.base.database.hawk

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.orhanobut.hawk.Storage

@Suppress("UNCHECKED_CAST")
@SuppressLint("CommitPrefEdits")
class HawkStorage(context: Context, tag: String) : Storage {

    private val preferences: SharedPreferences = context.getSharedPreferences(tag, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor
        get() = preferences.edit()

    override
    fun <T> put(key: String?, value: T): Boolean {
        if (value == null) throw NullPointerException("key should not be null")
        return editor.putString(key, value.toString()).commit()
    }

    override
    fun <T> get(key: String): T? {
        return preferences.getString(key, null) as T
    }

    override
    fun delete(key: String): Boolean {
        return editor.remove(key).commit()
    }

    override
    fun contains(key: String): Boolean {
        return preferences.contains(key)
    }

    override fun deleteAll(): Boolean {
        return editor.clear().commit()
    }

    override
    fun count(): Long {
        return preferences.all.size.toLong()
    }

}
