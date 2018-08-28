package com.app.blockchain.certnetwork.repo.network.model.request

import com.google.gson.annotations.SerializedName

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */
//{
//    "name": "Super Blockchain Developer",
//    "category": "Blockchain",
//    "desc": "This is Blockchain Developer certification",
//    "expiredDate": "12/01/2018",
//    "private": "0x3df56117c273ce0c66f15ed64a10c9fd7cb20e3365cb0bcd392ced8db0edb1ca",
//    "address": "0x31fa055F4b7B2C50eC625Af0eC0ebcDCCb0aEb9B"
//}
class AddCertBody(
        @SerializedName("name") var name: String?,
        @SerializedName("category") var category: String?,
        @SerializedName("desc") var desc: String?,
        @SerializedName("expiredDate") var expiredDate: String?,
        @SerializedName("private") var private: String?,
        @SerializedName("address") var address: String?
) {
}