package com.dcen.wallet.dellet.core.util.base58

interface Decoder {

    /**
     * Decodes given bytes as a number in base58.
     *
     * @param base58 string to decode
     * @return number as bytes
     */
    fun decode(base58: CharSequence): ByteArray
}