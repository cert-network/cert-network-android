package com.app.blockchain.certnetwork.repo.model

import com.app.blockchain.certnetwork.appcommon.base.repository.base.database.realm.CertDatabase
import com.lib.nextwork.engine.model.NextworkModel

class CertificateList(
        var certList: List<Certificate>
) : NextworkModel(CertDatabase.CERT_ID) {
    override
    fun shouldFetch(): Boolean {
        return false
    }

}