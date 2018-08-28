package com.dcen.wallet.dellet.core.util.base58

interface DecodeTarget {
    fun getWriterForLength(len: Int): DecodeWriter
}