package com.app.blockchain.certnetwork.repo.model

import android.os.Parcel
import com.app.blockchain.certnetwork.appcommon.base.repository.base.database.realm.CertDatabase
import com.app.blockchain.certnetwork.common.extension.KParcelable
import com.app.blockchain.certnetwork.common.extension.parcelableCreator
import com.lib.nextwork.engine.model.NextworkModel

data class Account(
        var name: String = "",
        var mobileNumber: String = "",
        var imageUrl: String = "",
        var passport: String = "",
        var firebaseUserId: String = "",
        var privateKey: String = "",
        var address: String = ""
) : NextworkModel(CertDatabase.ACCOUNT_ID), KParcelable {

    override
    fun shouldFetch(): Boolean {
        return false
    }

    constructor(source: Parcel) : this(
            source.readString() ?: "",
            source.readString() ?: "",
            source.readString() ?: "",
            source.readString() ?: "",
            source.readString() ?: "",
            source.readString() ?: "",
            source.readString() ?: ""
    )

    override
    fun describeContents() = 0

    override
    fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(mobileNumber)
        writeString(imageUrl)
        writeString(passport)
        writeString(firebaseUserId)
        writeString(privateKey)
        writeString(address)
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::Account)
    }
}