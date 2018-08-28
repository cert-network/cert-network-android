package com.dcen.wallet.dellet.core.bip32.crypto


import com.dcen.wallet.dellet.core.bip32.Secp256k1SC.removeUncompressPrefix
import com.dcen.wallet.dellet.core.bip32.network.DefaultNetwork
import com.dcen.wallet.dellet.core.util.Hex
import com.dcen.wallet.dellet.core.util.keccak256
import com.dcen.wallet.dellet.core.util.keccak256String
import com.dcen.wallet.dellet.core.validate.EthAddressValidate
import java.util.*

/**
 * Keccak-256 hash function.
 *
 * @param input binary encoded input data
 * @return hash value
 */


class Ethereum : Crypto(DefaultNetwork.MAIN_NET) {


    companion object {
        const val PREFIX = "0x"

        fun prependHexPrefix(input: String): String {
            return if (!EthAddressValidate.containsHexPrefix(input)) {
                PREFIX + input
            } else {
                input
            }
        }

        fun validateAddress(address: String): Boolean = prependHexPrefix(address) == toChecksumAddress(address)


        /**
         * Checksum address encoding as per
         * <a href="https://github.com/ethereum/EIPs/blob/master/EIPS/eip-55.md">EIP-55</a>.
         *
         * @param address a valid hex encoded address
         * @return hex encoded checksum address
         */
        fun toChecksumAddress(address: String): String {
            val lowercaseAddress = EthAddressValidate.cleanHexPrefix(address).toLowerCase()
            val addressHash = EthAddressValidate.cleanHexPrefix(lowercaseAddress.keccak256String)

            val result = StringBuilder(lowercaseAddress.length + 2)

            result.append(PREFIX)

            for (i in 0 until lowercaseAddress.length) {
                if (Integer.parseInt(addressHash[i].toString(), 16) >= 8) {
                    result.append(lowercaseAddress[i].toString().toUpperCase())
                } else {
                    result.append(lowercaseAddress[i])
                }
            }

            return result.toString()
        }


    }

    override
    val prefix: String = PREFIX

    override
    fun address(publicKeyCompress: ByteArray, publicKeyUncompress: ByteArray): String {
        val hash: ByteArray = removeUncompressPrefix(publicKeyUncompress).keccak256
        return toChecksumAddress(prependHexPrefix(Hex.toHexString(Arrays.copyOfRange(hash, hash.size - 20, hash.size))))
    }

    override
    fun validateAddress(address: String): Boolean = Ethereum.validateAddress(address)


}
