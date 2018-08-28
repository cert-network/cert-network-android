package com.dcen.wallet.dellet.core.validate

import java.math.BigInteger

/**
 * Created by「 The Khaeng 」on 18 Jul 2018 :)
 */
class EthAddressValidate : ValidateAddress() {
    companion object {

        const val PRIVATE_KEY_LENGTH = 32
        const val PUBLIC_KEY_LENGTH = 64

        const val ADDRESS_LENGTH = 160
        const val ADDRESS_LENGTH_IN_HEX = ADDRESS_LENGTH shr 2


        @JvmStatic
        fun containsHexPrefix(input: String?): Boolean {
            if (input == null) return false
            return input.isNotEmpty() && input.length > 1
                    && input[0] == '0' && input[1] == 'x'
        }

        @JvmStatic
        fun cleanHexPrefix(input: String?): String {
            return if (containsHexPrefix(input)) {
                input?.substring(2) ?: ""
            } else {
                input ?: ""
            }
        }

        @JvmStatic
        fun toBigIntNoPrefix(hexValue: String?): BigInteger {
            return BigInteger(hexValue ?: "", 16)
        }


    }


    override
    val PREFIX: String = "0x"

    override
    fun isValidAddress(address: String?): Boolean {
        val cleanInput = cleanHexPrefix(address)

        try {
            toBigIntNoPrefix(cleanInput)
        } catch (e: NumberFormatException) {
            return false
        }


        return cleanInput.length == ADDRESS_LENGTH_IN_HEX
    }



}