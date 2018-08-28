package com.app.blockchain.certnetwork.repo.network.model.request

import com.google.gson.annotations.SerializedName

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */
//{
//    "name": "Panupak Vichaidi",
//    "passport": "AA00000000",
//    "private": "0x3df56117c273ce0c66f15ed64a10c9fd7cb20e3365cb0bcd392ced8db0edb1ca",
//    "address": "0x31fa055F4b7B2C50eC625Af0eC0ebcDCCb0aEb9B"
//}
class AddUserBody(
        @SerializedName("name") var name: String?,
        @SerializedName("passport") var passport: String? = null,
        @SerializedName("private") var private: String?,
        @SerializedName("address") var address: String?
) {
}