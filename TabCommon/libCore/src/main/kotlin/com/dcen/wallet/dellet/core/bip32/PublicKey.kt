package com.dcen.wallet.dellet.core.bip32

import com.dcen.wallet.dellet.core.util.parse256
import org.spongycastle.util.encoders.Hex
import java.math.BigInteger

/**
 * Created by「 The Khaeng 」on 05 Jul 2018 :)
 */
class PublicKey constructor(private val hdKey: HdKey) : Key {
    override
    val byteArray: ByteArray
        get() = hdKey.key

    override
    val parse256: BigInteger
        get() = byteArray.parse256

    override
    val postEncrypt: String
        get() = hdKey.crypto?.postEncryptPublicKey(hdKey.key) ?: ""

    override
    fun toString(): String = (hdKey.crypto?.prefix ?: "") + Hex.toHexString(hdKey.key)
}
