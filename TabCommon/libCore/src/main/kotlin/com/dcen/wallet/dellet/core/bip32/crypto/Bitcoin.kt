package com.dcen.wallet.dellet.core.bip32.crypto


import com.dcen.wallet.dellet.core.bip32.network.BitcoinNetwork
import com.dcen.wallet.dellet.core.bip32.network.Network
import com.dcen.wallet.dellet.core.util.base58.Base58
import com.dcen.wallet.dellet.core.util.base58.Base58.base58Encode
import com.dcen.wallet.dellet.core.util.hash160into
import com.dcen.wallet.dellet.core.util.sha256Twice
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils
import java.util.*


open class Bitcoin(network: Network = BitcoinNetwork.MAIN_NET) : Crypto(network) {

    companion object {

        fun validateAddress(address: String): Boolean {
            if (address.length < 26 || address.length > 35)
                return false
            val decoded = decodeBase58To25Bytes(address) ?: return false
            val hash = Arrays.copyOfRange(decoded, 0, 21).sha256Twice
            return Arrays.equals(Arrays.copyOfRange(hash, 0, 4), Arrays.copyOfRange(decoded, 21, 25))
        }

        fun encodeWIF(privateKey: ByteArray): String {

            val tmpPrivateKey =
                    if (privateKey[0] == 0.toByte()) {
                        privateKey.copyOfRange(1, privateKey.size)
                    } else {
                        privateKey
                    }

            val valueToPrepend = ByteArray(1)
            valueToPrepend[0] = 0x80.toByte()

            val privateKeyWithExtraByte = ByteUtils.concatenate(valueToPrepend, tmpPrivateKey)

            val hashOfPrivateKey = privateKeyWithExtraByte.sha256Twice

            val checksum = hashOfPrivateKey.copyOfRange(0, 4)

            val convertedPrivateKey = ByteUtils.concatenate(privateKeyWithExtraByte, checksum)

            return Base58.base58Encode(convertedPrivateKey)
        }


        fun decodeWIF(key: String): ByteArray {

            val decodeBase58: ByteArray = Base58.base58Decode(key)

            val decodeBase58Drop4byte: ByteArray = decodeBase58.copyOfRange(0, decodeBase58.size - 4)

            return decodeBase58Drop4byte.copyOfRange(1, decodeBase58Drop4byte.size)
        }


        fun checksumWIF(wif: String): ByteArray {
            val decodeBase58: ByteArray = Base58.base58Decode(wif)

            val decodeBase58Drop4byte: ByteArray = decodeBase58.copyOfRange(0, decodeBase58.size - 4)

            val hashSha256: ByteArray = decodeBase58Drop4byte.sha256Twice

            return hashSha256.copyOfRange(0, 4)
        }


        private fun decodeBase58To25Bytes(input: String): ByteArray? {
            val result = ByteArray(25)
            val decodeBytes = Base58.base58Decode(input)
            System.arraycopy(decodeBytes, 0, result, result.size - decodeBytes.size, decodeBytes.size)
            return result
        }

    }

    override
    fun address(publicKeyCompress: ByteArray,
                publicKeyUncompress: ByteArray): String = p2pkhAddress(publicKeyCompress)

    override
    fun validateAddress(address: String): Boolean = Bitcoin.validateAddress(address)

    override
    fun postEncryptPrivateKey(privateKey: ByteArray): String = encodeWIF(privateKey)

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

}