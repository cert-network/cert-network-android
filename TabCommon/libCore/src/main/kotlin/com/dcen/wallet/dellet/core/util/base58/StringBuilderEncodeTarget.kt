package com.dcen.wallet.dellet.core.util.base58

internal class StringBuilderEncodeTarget : EncodeTarget, EncodeTargetCapacity {

    private val sb = StringBuilder()

    override
    fun setCapacity(characters: Int) {
        sb.ensureCapacity(characters)
    }

    override
    fun append(c: Char) {
        sb.append(c)
    }

    override
    fun toString(): String = sb.toString()

    fun clear() {
        sb.setLength(0)
    }
}