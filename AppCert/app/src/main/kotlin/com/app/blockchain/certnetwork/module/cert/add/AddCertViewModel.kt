package com.app.blockchain.certnetwork.module.cert.add


import com.app.blockchain.certnetwork.appcommon.base.mvvm.AppViewModel
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResource
import com.app.blockchain.certnetwork.repo.network.model.request.AddCertBody
import com.app.blockchain.certnetwork.repo.network.trigger.AddCertTrigger
import com.app.blockchain.certnetwork.repo.CertRepository
import com.lib.nextwork.engine.steam.TriggerObjectDataStream

class AddCertViewModel(
        var repo: CertRepository = CertRepository.getInstance()
) : AppViewModel() {

    val triggerAddCert =
            TriggerObjectDataStream<AddCertTrigger, AppResource<Boolean>>()
                    .initSwitchMap {
                        repo.addCert(it)
                    }

    fun requestAddCertificate(name: String,
                              category: String,
                              expiredDate: String,
                              desc: String) {
        val account = repo.loadAccount()
        account?.apply {
            triggerAddCert.trigger(AddCertTrigger(
                    addCertBody = AddCertBody(
                            name = name,
                            category = category,
                            desc = desc,
                            expiredDate = expiredDate,
                            private = account.privateKey,
                            address = account.address
                    )
            ))
        }
    }


}
