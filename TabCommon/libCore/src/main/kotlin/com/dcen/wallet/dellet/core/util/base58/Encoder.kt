package com.dcen.wallet.dellet.core.util.base58

interface Encoder {

    /**
     * Encodes given bytes as a number in base58.
     *
     * @param bytes bytes to encode
     * @return base58 string representation
     */
    fun encode(bytes: ByteArray): String
}