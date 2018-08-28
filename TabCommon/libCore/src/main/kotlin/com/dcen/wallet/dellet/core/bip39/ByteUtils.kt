package com.dcen.wallet.dellet.core.bip39

import kotlin.experimental.or

internal object ByteUtils {

    @JvmStatic
    fun next11Bits(bytes: ByteArray, offset: Int): Int {
        val skip = offset / 8
        val lowerBitsToRemove = 3 * 8 - 11 - offset % 8
        return ((((bytes[skip].toInt() and 0xff shl 16 or
                (bytes[skip + 1].toInt() and 0xff shl 8) or
                if (lowerBitsToRemove < 8)
                    bytes[skip + 2].toInt() and 0xff
                else
                    0)) shr lowerBitsToRemove) and (1 shl 11) - 1)
    }


    @JvmStatic
    fun writeNext11(bytes: ByteArray, value: Int, offset: Int) {
        val skip = offset / 8
        val bitSkip = offset % 8
        run {
            //byte 0
            val firstValue: Byte = bytes[skip]
            val toWrite: Byte = (value shr (3 + bitSkip)).toByte()
            bytes[skip] = firstValue or toWrite
        }

        run {
            //byte 1
            val valueInByte = bytes[skip + 1]
            val i = 5 - bitSkip
            val toWrite = (if (i > 0) (value shl i) else (value shr -i)).toByte()
            bytes[skip + 1] = valueInByte or toWrite
        }

        if (bitSkip >= 6) {//byte 2
            val valueInByte = bytes[skip + 2]
            val toWrite = (value shl 13 - bitSkip).toByte()
            bytes[skip + 2] = valueInByte or toWrite
        }
    }
}
