package com.dcen.wallet.dellet.core.sign.ethereum

import android.support.annotation.VisibleForTesting
import com.dcen.wallet.dellet.core.sign.core.ECKeyPair
import com.dcen.wallet.dellet.core.sign.core.Sign
import com.dcen.wallet.dellet.core.sign.core.Signer
import com.dcen.wallet.dellet.core.sign.ethereum.rlp.*
import com.dcen.wallet.dellet.core.util.Hex
import com.dcen.wallet.dellet.core.util.keccak256
import com.dcen.wallet.dellet.core.util.shl
import com.dcen.wallet.dellet.core.validate.EthAddressValidate.Companion.cleanHexPrefix
import java.util.*

/**
 * Created by「 The Khaeng 」on 20 Jul 2018 :)
 */
class EthereumSigner(val sign: Sign = Sign()) : Signer<EthereumRawTransaction> {

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
            rawTransaction: EthereumRawTransaction, keyPair: ECKeyPair, chainId: Byte?): ByteArray {
        return signMessage(rawTransaction, keyPair, chainId)
//        return signedMessage.keccak256
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
            rawTransaction: EthereumRawTransaction, keyPair: ECKeyPair, chainId: Byte?): String {
        return Hex.toHexString(generateTransactionHash(rawTransaction, keyPair, chainId))
    }

    override
    fun signMessage(rawTransaction: EthereumRawTransaction, keyPair: ECKeyPair, chainId: Byte?): ByteArray {
        if (chainId != null) {
            rawTransaction.signatureData = Sign.SignatureData(chainId, byteArrayOf(), byteArrayOf())
        }
        val encodedTransaction = encode(rawTransaction, rawTransaction.signatureData)
        val messageHash: ByteArray = encodedTransaction.keccak256
        val signatureData = sign.signMessage(messageHash, keyPair)
        rawTransaction.signatureData = signatureData
        if (chainId != null) {
            rawTransaction.signatureData = createEip155SignatureData(signatureData, chainId)
        }
        return encode(rawTransaction, rawTransaction.signatureData)
    }

    fun createEip155SignatureData(
            signatureData: Sign.SignatureData, chainId: Byte): Sign.SignatureData {
        val v = (signatureData.v + (chainId shl 1) + 8).toByte()
        return Sign.SignatureData(v, signatureData.r, signatureData.s)
    }


    /* =========================== Encode method ================================================ */
    override
    fun encode(rawTransaction: EthereumRawTransaction, signature: Sign.SignatureData?): ByteArray {
        val values = asRlpValues(rawTransaction, signature)
        val rlpList = RlpList(values)
        return RlpEncoder.encode(rlpList)
    }

    @VisibleForTesting
    fun asRlpValues(
            rawTransaction: EthereumRawTransaction, signature: Sign.SignatureData? = null): List<RlpType> {
        val result = ArrayList<RlpType>()

        result.add(RlpString.create(rawTransaction.nonce))
        result.add(RlpString.create(rawTransaction.gasPrice))
        result.add(RlpString.create(rawTransaction.gasLimit))

        // an empty to address (contract creation) should not be encoded as a numeric 0 value
        val to = rawTransaction.to
        if (to.isNotEmpty()) {
            // addresses that start with zeros should be encoded with the zeros included, not
            // as numeric values
            result.add(RlpString.create(hexStringToByteArray(to)))
        } else {
            result.add(RlpString.create(""))
        }

        result.add(RlpString.create(rawTransaction.value))

        // value field will already be hex encoded, so we need to convert into binary first
        val data = hexStringToByteArray(rawTransaction.data)
        result.add(RlpString.create(data))

        if (signature != null) {
            result.add(RlpString.create(signature.v))
            result.add(RlpString.create(trimLeadingZeroes(signature.r)))
            result.add(RlpString.create(trimLeadingZeroes(signature.s)))
        }

        return result
    }


    private fun trimLeadingZeroes(bytes: ByteArray): ByteArray {
        var offset = 0
        while (offset < bytes.size - 1) {
            if (bytes[offset] != 0.toByte()) {
                break
            }
            offset++
        }
        return Arrays.copyOfRange(bytes, offset, bytes.size)
    }

    /* =========================== Decode method ================================================ */
    override
    fun decode(hexTransaction: String): EthereumRawTransaction {
        val transaction = hexStringToByteArray(hexTransaction)
        val rlpList = RlpDecoder.decode(transaction)
        val values = rlpList.values[0] as RlpList
        val nonce = (values.values[0] as RlpString).toPositiveBigInteger()
        val gasPrice = (values.values[1] as RlpString).toPositiveBigInteger()
        val gasLimit = (values.values[2] as RlpString).toPositiveBigInteger()
        val to = (values.values[3] as RlpString).toString()
        val value = (values.values[4] as RlpString).toPositiveBigInteger()
        val data = (values.values[5] as RlpString).toString()
        return if (values.values.size > 6) {
            val v = (values.values[6] as RlpString).bytes[0]
            val r = zeroPadded((values.values[7] as RlpString).bytes, 32)
            val s = zeroPadded((values.values[8] as RlpString).bytes, 32)
            val signatureData = Sign.SignatureData(v, r, s)
            EthereumRawTransaction(nonce, gasPrice, gasLimit,
                    to, value, data, signatureData)
        } else {
            EthereumRawTransaction.createTransaction(nonce,
                    gasPrice, gasLimit, to, value, data)
        }
    }

    private fun zeroPadded(value: ByteArray, size: Int): ByteArray {
        if (value.size == size) {
            return value
        }
        val diff = size - value.size
        val paddedValue = ByteArray(size)
        System.arraycopy(value, 0, paddedValue, diff, value.size)
        return paddedValue
    }

    fun hexStringToByteArray(input: String): ByteArray {
        val cleanInput = cleanHexPrefix(input)

        val len = cleanInput.length

        if (len == 0) {
            return byteArrayOf()
        }

        val data: ByteArray
        val startIdx: Int
        if (len % 2 != 0) {
            data = ByteArray(len / 2 + 1)
            data[0] = Character.digit(cleanInput.get(0), 16).toByte()
            startIdx = 1
        } else {
            data = ByteArray(len / 2)
            startIdx = 0
        }

        var i = startIdx
        while (i < len) {
            data[(i + 1) / 2] = ((Character.digit(cleanInput.get(i), 16) shl 4) + Character.digit(cleanInput.get(i + 1), 16)).toByte()
            i += 2
        }
        return data
    }

}