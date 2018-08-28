package com.app.blockchain.certnetwork.module.main


import com.app.blockchain.certnetwork.appcommon.base.mvvm.AppSharedViewModel
import com.app.blockchain.certnetwork.appcommon.base.repository.base.model.AppResource
import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseItem
import com.app.blockchain.certnetwork.module.main.adapter.operator.CertItemCreator
import com.app.blockchain.certnetwork.repo.network.trigger.GetCertListTrigger
import com.app.blockchain.certnetwork.repo.CertRepository
import com.app.blockchain.certnetwork.repo.model.Certificate
import com.app.blockchain.certnetwork.repo.model.CertificateList
import com.lib.nextwork.engine.steam.TriggerObjectDataStream


class MainViewModel(
        var certItemList: MutableList<BaseItem> = mutableListOf(),
        var repo: CertRepository = CertRepository.getInstance()
) : AppSharedViewModel() {

    val triggerCertList =
            TriggerObjectDataStream<GetCertListTrigger, AppResource<CertificateList>>()
                    .initSwitchMap {
                        repo.getCertList(it)
                    }


    fun requestCertList(forceFetch: Boolean = true) {
        val account = repo.loadAccount()
        account?.apply {
            triggerCertList.trigger(GetCertListTrigger(
                    address = account.address,
                    isForceFetch = forceFetch
            ))
        }
    }


    fun createItemList(list: List<Certificate>?): MutableList<BaseItem> {
        return CertItemCreator.create(repo.loadAccount(), list)
    }

    fun createEmptyItemList(): MutableList<BaseItem> {
        return CertItemCreator.createEmpty(repo.loadAccount())
    }

    fun initCertItem() {
        this.certItemList = CertItemCreator.createHeader(repo.loadAccount())
    }


}
