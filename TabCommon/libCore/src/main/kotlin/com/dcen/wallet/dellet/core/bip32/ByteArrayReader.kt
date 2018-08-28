package com.dcen.wallet.dellet.core.bip32

import java.util.*
import kotlin.experimental.and

internal class ByteArrayReader(private val bytes: ByteArray) {
    private var idx = 0

    fun readRange(length: Int): ByteArray {
        val range = Arrays.copyOfRange(this.bytes, idx, idx + length)
        idx += length
        return range
    }

    /**
     * deserialize a 32-bit unsigned integer i as a 4-byte sequence, most significant byte first.
     */
    fun readSer32(): Int {
        var result = read()
        result = result shl 8
        result = result or read()
        result = result shl 8
        result = result or read()
        result = result shl 8
        result = result or read()
        return result
    }

    fun read(): Int {
        return (0xff.toByte() and bytes[idx++]).toInt()
    }
}