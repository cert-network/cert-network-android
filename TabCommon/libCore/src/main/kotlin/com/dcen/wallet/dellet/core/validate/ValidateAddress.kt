package com.dcen.wallet.dellet.core.validate

/**
 * Created by「 The Khaeng 」on 18 Jul 2018 :)
 */
abstract class ValidateAddress {

    open val PREFIX = ""

    abstract fun isValidAddress(address: String?): Boolean
}