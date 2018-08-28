package com.dcen.wallet.dellet.core.sign.ethereum.rlp

import java.util.*

/**
 *
 * Recursive Length Prefix (RLP) encoder.
 *
 *
 *
 * For the specification, refer to p16 of the [
 * yellow paper](http://gavwood.com/paper.pdf) and [here](https://github.com/ethereum/wiki/wiki/RLP).
 */
object RlpEncoder {

    fun encode(value: RlpType): ByteArray {
        return if (value is RlpString) {
            encodeString(value)
        } else {
            encodeList(value as RlpList)
        }
    }

    private fun encode(bytesValue: ByteArray, offset: Int): ByteArray {
        if (bytesValue.size == 1
                && offset == RlpDecoder.OFFSET_SHORT_STRING
                && bytesValue[0] >= 0x00.toByte()
                && bytesValue[0] <= 0x7f.toByte()) {
            return bytesValue
        } else if (bytesValue.size <= 55) {
            val result = ByteArray(bytesValue.size + 1)
            result[0] = (offset + bytesValue.size).toByte()
            System.arraycopy(bytesValue, 0, result, 1, bytesValue.size)
            return result
        } else {
            val encodedStringLength = toMinimalByteArray(bytesValue.size)
            val result = ByteArray(bytesValue.size + encodedStringLength.size + 1)

            result[0] = (offset + 0x37 + encodedStringLength.size).toByte()
            System.arraycopy(encodedStringLength, 0, result, 1, encodedStringLength.size)
            System.arraycopy(
                    bytesValue, 0, result, encodedStringLength.size + 1, bytesValue.size)
            return result
        }
    }

    private fun encodeString(value: RlpString): ByteArray {
        return encode(value.bytes, RlpDecoder.OFFSET_SHORT_STRING)
    }

    private fun toMinimalByteArray(value: Int): ByteArray {
        val encoded = toByteArray(value)

        for (i in encoded.indices) {
            if (encoded[i] != 0.toByte()) {
                return Arrays.copyOfRange(encoded, i, encoded.size)
            }
        }

        return byteArrayOf()
    }

    private fun toByteArray(value: Int): ByteArray {
        return byteArrayOf(
                ((value shr 24) and 0xff).toByte(),
                ((value shr 16) and 0xff).toByte(),
                ((value shr 8) and 0xff).toByte(),
                (value and 0xff).toByte()
        )
    }

    private fun encodeList(value: RlpList): ByteArray {
        val values = value.values
        return if (values.isEmpty()) {
            encode(byteArrayOf(), RlpDecoder.OFFSET_SHORT_LIST)
        } else {
            var result = ByteArray(0)
            for (entry in values) {
                result = concat(result, encode(entry))
            }
            encode(result, RlpDecoder.OFFSET_SHORT_LIST)
        }
    }

    private fun concat(b1: ByteArray, b2: ByteArray): ByteArray {
        val result = Arrays.copyOf(b1, b1.size + b2.size)
        System.arraycopy(b2, 0, result, b1.size, b2.size)
        return result
    }
}
