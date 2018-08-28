package com.dcen.wallet.dellet.core.bip32

import java.util.*

internal class ByteArrayWriter(
        private val bytes: ByteArray
) {

    companion object {

        @JvmStatic
        fun tail32(bytes64: ByteArray): ByteArray {
            val ir = ByteArray(bytes64.size - 32)
            System.arraycopy(bytes64, 32, ir, 0, ir.size)
            return ir
        }

        @JvmStatic
        fun head32(bytes64: ByteArray): ByteArray {
            return Arrays.copyOf(bytes64, 32)
        }
    }

    private var idx = 0

    @JvmOverloads
    fun concat(bytesSource: ByteArray, length: Int = bytesSource.size) {
        System.arraycopy(bytesSource, 0, bytes, idx, length)
        idx += length
    }

    /**
     * ser32(i): serialize a 32-bit unsigned integer i as a 4-byte sequence, most significant byte first.
     *
     * @param i a 32-bit unsigned integer
     */
    fun concatSer32(i: Int) {
        concat((i shr 24).toByte())
        concat((i shr 16).toByte())
        concat((i shr 8).toByte())
        concat(i.toByte())
    }

    fun concat(b: Byte) {
        bytes[idx++] = b
    }

}