package com.dcen.wallet.dellet.core.util.base58

interface WorkingBuffer {

    fun setCapacity(atLeast: Int)

    operator fun get(index: Int): Byte

    fun put(index: Int, value: Byte)

    fun clear()
}