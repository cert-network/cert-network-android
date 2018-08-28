package com.app.blockchain.certnetwork.module.main.adapter.operator


import com.app.blockchain.certnetwork.common.base.view.holder.base.BaseItem
import com.app.blockchain.certnetwork.module.main.adapter.item.CertCardItem
import com.app.blockchain.certnetwork.module.main.adapter.item.EmptyCertItem
import com.app.blockchain.certnetwork.module.main.adapter.item.ProfileItem
import com.app.blockchain.certnetwork.repo.model.Account
import com.app.blockchain.certnetwork.repo.model.Certificate
import java.util.*

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

object CertItemCreator {
    const val TYPE_PROFILE_ITEM = 10
    const val TYPE_CERT_ITEM = 11
    const val TYPE_EMPTY_CERT = 12

    fun createHeader(account: Account?): MutableList<BaseItem> {
        val itemList = ArrayList<BaseItem>()
        account?.apply {
            itemList.add(ProfileItem(0)
                    .setName(account.name)
                    .setMobileNumber(account.mobileNumber)
                    .setAddress(account.address))
        }
        return itemList
    }

    fun createEmpty(account: Account?): MutableList<BaseItem> {
        val itemList = ArrayList<BaseItem>()
        account?.apply {
            itemList.addAll(createHeader(account))
            itemList.add(EmptyCertItem(100))
        }
        return itemList
    }

    fun create(account: Account?, list: List<Certificate>?): MutableList<BaseItem> {
        val itemList = ArrayList<BaseItem>()
        account?.apply {
            itemList.addAll(createHeader(account))
            itemList.addAll(
                    list?.mapIndexed { _, certificate ->
                        CertCardItem(certificate.certId.hashCode())
                                .setApprove(certificate.isApprove)
                                .setName(account.name)
                                .setCategory(certificate.category)
                                .setCertificateName(certificate.certName)
                                .setApproveBy(certificate.approveBy)

                    } ?: mutableListOf())
        }
        return itemList
    }

    fun mockItem(account: Account?): MutableList<BaseItem> {
        val itemList = ArrayList<BaseItem>()
        account?.apply {
            itemList.add(ProfileItem(0)
                    .setName(account.name)
                    .setMobileNumber(account.mobileNumber)
                    .setAddress(account.address))
            itemList.add(CertCardItem(1)
                    .setApprove(false)
                    .setName(account.name)
                    .setCategory("Technologies")
                    .setCertificateName("Master of Engineer")
                    .setApproveBy("Unknown"))
            itemList.add(CertCardItem(2)
                    .setApprove(true)
                    .setName(account.name)
                    .setCategory("Technologies")
                    .setCertificateName("Master of Engineer")
                    .setApproveBy("Unknown"))

        }
        return itemList
    }


}
