package com.app.blockchain.certnetwork.repo.network.trigger

import com.app.blockchain.certnetwork.repo.network.model.request.AddUserBody
import com.lib.nextwork.engine.Trigger


/**
 * Created by「 The Khaeng 」on 01 Feb 2018 :)
 */
class AddUserTrigger(
        val addUserBody: AddUserBody
) : Trigger(true) {

}

