package com.dcen.wallet.dellet.core.util.base58

import java.util.*

internal class ByteArrayWorkingBuffer : WorkingBuffer {

    companion object {

        private val EMPTY = ByteArray(0)

    }

    private var bytes = EMPTY

    override
    fun setCapacity(atLeast: Int) {
        bytes = ensureCapacity(bytes, atLeast)
        clear(bytes)
    }

    override
    fun get(index: Int): Byte = bytes[index]

    override
    fun put(index: Int, value: Byte) {
        bytes[index] = value
    }

    override
    fun clear() {
        clear(bytes)
    }

    private
    fun ensureCapacity(bytes: ByteArray, atLeast: Int): ByteArray {
        if (bytes.size >= atLeast) {
            return bytes
        }
        clear(bytes)
        return ByteArray(atLeast)
    }

    private fun clear(bytes: ByteArray) {
        Arrays.fill(bytes, 255.toByte())
    }


}