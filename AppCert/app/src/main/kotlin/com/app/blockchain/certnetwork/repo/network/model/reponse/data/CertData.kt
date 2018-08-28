package com.app.blockchain.certnetwork.repo.network.model.reponse.data

import com.app.blockchain.certnetwork.appcommon.base.repository.base.network.BaseData
import com.google.gson.annotations.SerializedName

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */
//"certId": "0x73a1adb2b90a739356bc799a048dbf2fc0ac13ce3076ea272549b646abd15586",
//"certCreator": "0x31fa055F4b7B2C50eC625Af0eC0ebcDCCb0aEb9B",
//"certName": "Super Blockchain Developer 4.0",
//"category": "Blockchain",
//"desc": "This is Blockchain Developer certification",
//"issueDate": "26/08/2018",
//"expiredDate": "12/01/2018",
//"approveBy": "Panupak Vichaidit",
//"isApprove": false
class CertData(
        @SerializedName("certId") var certId: String?,
        @SerializedName("certName") var certName: String?,
        @SerializedName("category") var category: String?,
        @SerializedName("desc") var desc: String?,
        @SerializedName("issueDate") var issueDate: String?,
        @SerializedName("expiredDate") var expiredDate: String?,
        @SerializedName("approveBy") var approveBy: String?,
        @SerializedName("isApprove") var isApprove: Boolean?
): BaseData() {
}