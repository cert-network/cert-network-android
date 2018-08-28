package com.dcen.wallet.dellet.core.bip32.crypto

import com.dcen.wallet.dellet.core.bip32.network.Network

/**
 * Created by「 The Khaeng 」on 04 Jul 2018 :)
 */
abstract class Crypto(
        var network: Network
) {

    val privateVersion: Int
        get() = network.getPrivateVersion()

    val publicVersion: Int
        get() = network.getPublicVersion()

    val p2pkhVersion: Byte
        get() = network.p2pkhVersion()

    val p2shVersion: Byte
        get() = network.p2shVersion()

    open val prefix: String = ""

    open fun postEncryptPrivateKey(privateKey: ByteArray): String = ""

    open fun postEncryptPublicKey(publicKey: ByteArray): String = ""


    abstract fun address(publicKeyCompress: ByteArray, publicKeyUncompress: ByteArray): String

    abstract fun validateAddress(address: String): Boolean

}
