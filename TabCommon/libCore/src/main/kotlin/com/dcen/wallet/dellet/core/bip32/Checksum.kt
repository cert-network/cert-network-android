package com.dcen.wallet.dellet.core.bip32


import com.dcen.wallet.dellet.core.bip32.exception.BadKeySerializationException
import com.dcen.wallet.dellet.core.util.sha256Twice

internal object Checksum {

    @JvmStatic
    fun confirmExtendedKeyChecksum(extendedKeyData: ByteArray) {
        val expected = checksum(extendedKeyData)
        for (i in 0..3) {
            if (extendedKeyData[78 + i] != expected[i])
                throw BadKeySerializationException("Checksum error")
        }
    }

    @JvmStatic
    fun checksum(privateKey: ByteArray): ByteArray = privateKey.sha256Twice(0, 78)
}