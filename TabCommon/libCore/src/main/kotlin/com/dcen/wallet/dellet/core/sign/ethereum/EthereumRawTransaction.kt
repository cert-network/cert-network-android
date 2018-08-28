package com.dcen.wallet.dellet.core.sign.ethereum

import com.dcen.wallet.dellet.core.bip32.crypto.Ethereum
import com.dcen.wallet.dellet.core.sign.core.RawTransaction
import com.dcen.wallet.dellet.core.sign.core.Sign
import com.dcen.wallet.dellet.core.util.keccak256
import com.dcen.wallet.dellet.core.validate.EthAddressValidate
import java.math.BigInteger
import java.security.SignatureException

class EthereumRawTransaction(
        val nonce: BigInteger,
        val gasPrice: BigInteger,
        val gasLimit: BigInteger,
        val to: String,
        val value: BigInteger,
        data: String?,
        var signatureData: Sign.SignatureData? = null) : RawTransaction() {

    var data: String = ""
        private set

    init {
        this.data = EthAddressValidate.cleanHexPrefix(data)
    }

    companion object {
        private val CHAIN_ID_INC = 35
        private val LOWER_REAL_V = 27

        fun createContractTransaction(
                nonce: BigInteger,
                gasPrice: BigInteger,
                gasLimit: BigInteger,
                value: BigInteger,
                init: String): EthereumRawTransaction {

            return EthereumRawTransaction(nonce, gasPrice, gasLimit, "", value, init)
        }

        fun createEtherTransaction(
                nonce: BigInteger,
                gasPrice: BigInteger,
                gasLimit: BigInteger,
                to: String,
                value: BigInteger): EthereumRawTransaction {

            return EthereumRawTransaction(nonce, gasPrice, gasLimit, to, value, "")

        }

        fun createTransaction(
                nonce: BigInteger,
                gasPrice: BigInteger,
                gasLimit: BigInteger,
                to: String,
                value: BigInteger,
                data: String): EthereumRawTransaction {

            return EthereumRawTransaction(nonce, gasPrice, gasLimit, to, value, data)
        }
    }


    private val sign: Sign = Sign()
    private val ethereum = Ethereum()

    val from: String?
        @Throws(SignatureException::class)
        get() {
            val chainId = chainId
            val transactionEncoder = EthereumSigner(sign)
            val encodedTransaction: ByteArray =
                    if (chainId == null) {
                        transactionEncoder.encode(this)
                    } else {
                        transactionEncoder.encode(this, Sign.SignatureData(chainId.toByte(), byteArrayOf(), byteArrayOf()))
                    }
            signatureData?.apply {
                val signatureDataV = Sign.SignatureData(getRealV(v), r, s)
                val messageHash = encodedTransaction.keccak256
                val key = sign.signedMessageToKey(messageHash, signatureDataV)
                return ethereum.address(
                        key[Sign.COMPRESS_INDEX].toByteArray(),
                        key[Sign.UNCOMPRESS_INDEX].toByteArray())
            }
            return null
        }

    val chainId: Int?
        get() {
            val v = signatureData?.v ?: return null
            return if (v.toInt() == LOWER_REAL_V || v.toInt() == LOWER_REAL_V + 1) {
                null
            } else (v - CHAIN_ID_INC) / 2
        }

    @Throws(SignatureException::class)
    fun verify(from: String) {
        if (this.from != from) {
            throw SignatureException("from mismatch")
        }
    }

    private fun getRealV(v: Byte): Byte {
        if (v.toInt() == LOWER_REAL_V || v.toInt() == LOWER_REAL_V + 1) {
            return v
        }
        val realV = LOWER_REAL_V.toByte()
        var inc = 0
        if (v.toInt() % 2 == 0) {
            inc = 1
        }
        return (realV + inc).toByte()
    }

}
