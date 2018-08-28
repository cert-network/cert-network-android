package com.dcen.wallet.dellet.core.sign.bitcoin

import com.dcen.wallet.dellet.core.sign.core.ECKeyPair
import com.dcen.wallet.dellet.core.sign.core.Sign
import com.dcen.wallet.dellet.core.sign.core.Signer

/**
 * Created by「 The Khaeng 」on 20 Jul 2018 :)
 */
class BitcoinSigner(val sign: Sign = Sign()) : Signer<BitcoinRawTransaction> {


    /**
     * Utility method to provide the transaction hash for a given transaction.
     *
     * @param rawTransaction we wish to send
     * @param credentials of the sender
     * @param chainId of the intended chain
     * @return encoded transaction hash
     */
    override
    fun generateTransactionHash(
            rawTransaction: BitcoinRawTransaction, keyPair: ECKeyPair, chainId: Byte?): ByteArray {
        return ByteArray(0)
    }

    /**
     * Utility method to provide the transaction hash for a given transaction.
     *
     * @param rawTransaction we wish to send
     * @param credentials of the sender
     * @param chainId of the intended chain
     * @return transaction hash as a hex encoded string
     */
    override
    fun generateTransactionHashHexEncoded(
            rawTransaction: BitcoinRawTransaction, keyPair: ECKeyPair, chainId: Byte?): String {
        return ""
    }

    override
    fun signMessage(rawTransaction: BitcoinRawTransaction, keyPair: ECKeyPair, chainId: Byte?): ByteArray {
        return ByteArray(0)
    }


    /* =========================== Encode method ================================================ */
//    override
//    fun encode(rawTransaction: BitcoinRawTransaction, chainId: Byte?): ByteArray {
//        return ByteArray(0)
//    }

    override
    fun encode(rawTransaction: BitcoinRawTransaction, signature: Sign.SignatureData?): ByteArray {
        return ByteArray(0)
    }

    /* =========================== Decode method ================================================ */
    override
    fun decode(hexTransaction: String): BitcoinRawTransaction {
        return BitcoinRawTransaction()
    }


}