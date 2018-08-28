package com.dcen.wallet.dellet.core.util.base58

import java.nio.ByteBuffer
import java.security.SecureRandom
import kotlin.experimental.xor

internal class SecureWorkingBuffer : WorkingBuffer {

    private var bytes: ByteBuffer? = null
    private val key = ByteArray(1021)

    init {
        SecureRandom().nextBytes(key)
    }

    override
    fun setCapacity(atLeast: Int) {
        bytes = ensureCapacity(bytes, atLeast)
        bytes?.let { clear(it) }
    }

    override
    fun get(index: Int): Byte {
        assertIndexValid(index)
        return encodeDecode(bytes?.get(index) ?: 0.toByte(), index)
    }

    override
    fun put(index: Int, value: Byte) {
        assertIndexValid(index)
        bytes?.put(index, encodeDecode(value, index))
    }

    override
    fun clear() {
        bytes?.let { clear(it) }
    }

    private fun assertIndexValid(index: Int) {
        if (index < 0 || index >= capacity())
            throw IndexOutOfBoundsException()
    }

    private fun capacity(): Int {
        return if (bytes == null) 0 else bytes?.capacity() ?: 0
    }

    private fun ensureCapacity(bytes: ByteBuffer?, atLeast: Int): ByteBuffer {
        if (bytes != null && bytes.capacity() >= atLeast) {
            return bytes
        }
        if (bytes != null)
            clear(bytes)
        return ByteBuffer.allocateDirect(atLeast)
    }

    private fun clear(bytes: ByteBuffer) {
        bytes.position(0)
        val capacity = bytes.capacity()
        for (i in 0 until capacity) {
            bytes.put(i, encodeDecode(255.toByte(), i))
        }
    }

    private fun encodeDecode(b: Byte, index: Int): Byte {
        return b xor key[index % key.size]
    }
}