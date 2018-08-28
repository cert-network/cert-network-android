package com.dcen.wallet.dellet.core.sign.core

/**
 * Created by「 The Khaeng 」on 20 Jul 2018 :)
 */
interface Signer<T: RawTransaction> {

    /**
     * Utility method to provide the transaction hash for a given transaction.
     *
     * @param rawTransaction we wish to send
     * @param credentials of the sender
     * @return encoded transaction hash
     */
    fun generateTransactionHash(rawTransaction: T, keyPair: ECKeyPair, chainId: Byte? = null): ByteArray

    /**
     * Utility method to provide the transaction hash for a given transaction.
     *
     * @param rawTransaction we wish to send
     * @param credentials of the sender
     * @return transaction hash as a hex encoded string
     */
    fun generateTransactionHashHexEncoded(rawTransaction: T, keyPair: ECKeyPair, chainId: Byte? = null): String


    fun signMessage(rawTransaction: T, keyPair: ECKeyPair, chainId: Byte? = null): ByteArray


    /* =========================== Encode method ================================================ */
//    fun encode(rawTransaction: T, chainId: Byte? = null): ByteArray

    fun encode(rawTransaction: T, signature: Sign.SignatureData? = null): ByteArray


    /* =========================== Decode method ================================================ */
    fun decode(hexTransaction: String): T


}