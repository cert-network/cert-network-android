package com.dcen.wallet.dellet.core.util.base58

class ByteArrayTarget : DecodeTarget {
    private var idx = 0
    private var bytes: ByteArray? = null

    override
    fun getWriterForLength(len: Int): DecodeWriter {
        bytes = ByteArray(len)
        return object : DecodeWriter {
            override
            fun append(b: Byte) {
                bytes?.set(idx++, b)
            }
        }
    }

    fun asByteArray(): ByteArray = bytes ?: ByteArray(0)
}