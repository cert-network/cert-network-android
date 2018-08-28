package com.dcen.wallet.dellet.core.util.base58

interface SecureDecoder {

    /**
     * Decodes given bytes as a number in base58.
     *
     * @param base58 string to decode
     * @param target Receiver for output
     */
    fun decode(base58: CharSequence, target: DecodeTarget)
}