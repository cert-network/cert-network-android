package com.dcen.wallet.dellet.core.sign.ethereum.rlp

import com.dcen.wallet.dellet.core.util.Hex
import java.math.BigInteger
import java.util.*

/**
 * RLP string type.
 */
class RlpString private constructor(val bytes: ByteArray) : RlpType {

    companion object {
        private val EMPTY = byteArrayOf()

        fun create(value: ByteArray): RlpString {
            return RlpString(value)
        }

        fun create(value: Byte): RlpString {
            return RlpString(byteArrayOf(value))
        }

        fun create(value: BigInteger): RlpString {
            // RLP encoding only supports positive integer values
            return if (value.signum() < 1) {
                RlpString(EMPTY)
            } else {
                val bytes = value.toByteArray()
                if (bytes[0].toInt() == 0) {  // remove leading zero
                    RlpString(Arrays.copyOfRange(bytes, 1, bytes.size))
                } else {
                    RlpString(bytes)
                }
            }
        }

        fun create(value: Long): RlpString {
            return create(BigInteger.valueOf(value))
        }

        fun create(value: String): RlpString {
            return RlpString(value.toByteArray())
        }
    }


    fun toPositiveBigInteger(): BigInteger {
        return if (bytes.isEmpty()) {
            BigInteger.ZERO
        } else {
            BigInteger(1, bytes)
        }
    }

    override
    fun toString(): String {
        return Hex.toHexString(bytes)
    }

    override
    fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val rlpString = o as RlpString?

        return Arrays.equals(bytes, rlpString!!.bytes)
    }

    override
    fun hashCode(): Int {
        return Arrays.hashCode(bytes)
    }

}
