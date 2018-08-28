package com.app.blockchain.certnetwork.repo.model

/**
 * Created by「 The Khaeng 」on 26 Aug 2018 :)
 */
class Certificate(
        var certId: String = "",
        var certCreator: String = "",
        var certName: String = "",
        var category: String = "",
        var desc: String = "",
        var issueDate: String = "",
        var expiredDate: String = "",
        var approveBy: String = "",
        var isApprove: Boolean = false
) {
}