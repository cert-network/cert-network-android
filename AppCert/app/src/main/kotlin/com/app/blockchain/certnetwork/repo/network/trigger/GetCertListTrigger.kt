package com.app.blockchain.certnetwork.repo.network.trigger

import com.lib.nextwork.engine.Trigger


/**
 * Created by「 The Khaeng 」on 01 Feb 2018 :)
 */
class GetCertListTrigger(
        val address: String,
        isForceFetch: Boolean
) : Trigger(isForceFetch) {

}

