package com.dcen.wallet.dellet.core.bip32

import java.math.BigInteger

/**
 * Created by「 The Khaeng 」on 05 Jul 2018 :)
 */
interface Key {
    val postEncrypt: String

    val byteArray: ByteArray

    val parse256: BigInteger
}
