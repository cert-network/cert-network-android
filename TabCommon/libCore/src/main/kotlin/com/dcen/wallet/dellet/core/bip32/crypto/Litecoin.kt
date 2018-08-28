package com.dcen.wallet.dellet.core.bip32.crypto


import com.dcen.wallet.dellet.core.bip32.network.LitecoinNetwork
import com.dcen.wallet.dellet.core.util.base58.Base58.base58Encode
import com.dcen.wallet.dellet.core.util.hash160into
import com.dcen.wallet.dellet.core.util.sha256Twice

class Litecoin : Crypto(LitecoinNetwork.MAIN_NET) {

    override
    fun address(publicKeyCompress: ByteArray, publicKeyUncompress: ByteArray): String =
            p2pkhAddress(publicKeyUncompress)

    fun p2pkhAddress(publicKey: ByteArray): String = encodeAddress(p2pkhVersion, publicKey)

    fun p2shAddress(publicKey: ByteArray): String {
        val script = ByteArray(22)
        script[1] = 20.toByte()
        script.hash160into(2, publicKey)
        return encodeAddress(p2shVersion, script)
    }

    private fun encodeAddress(version: Byte, data: ByteArray): String {
        val address = ByteArray(25)
        address[0] = version
        address.hash160into(1, data)
        System.arraycopy(address.sha256Twice(0, 21), 0, address, 21, 4)
        return base58Encode(address)
    }

    override
    fun validateAddress(address: String): Boolean {
        // TODO1: 3/8/2018 validate litecoin address
        return false
    }


}