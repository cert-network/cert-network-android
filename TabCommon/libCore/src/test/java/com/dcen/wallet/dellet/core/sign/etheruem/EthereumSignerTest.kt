package com.dcen.wallet.dellet.core.sign.etheruem

import com.dcen.wallet.dellet.core.sign.SignExample
import com.dcen.wallet.dellet.core.sign.core.ECKeyPair
import com.dcen.wallet.dellet.core.sign.core.Sign
import com.dcen.wallet.dellet.core.sign.ethereum.EthereumRawTransaction
import com.dcen.wallet.dellet.core.sign.ethereum.EthereumSigner
import com.dcen.wallet.dellet.core.sign.ethereum.rlp.RlpString
import com.dcen.wallet.dellet.core.sign.ethereum.rlp.RlpType
import com.dcen.wallet.dellet.core.util.Hex
import com.dcen.wallet.dellet.core.util.keccak256
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.*
import org.junit.Test
import java.math.BigInteger

class EthereumSignerTest {

    private val ethSigner: EthereumSigner = EthereumSigner()
    private val signTransaction: Sign = Sign()

    @Test
    fun generateTransactionHash() {
        val signedMessage = ethSigner.generateTransactionHash(createEtherTransaction(), SignExample.KEY_PAIR)
        val hexMessage = Hex.toHexString(signedMessage)
        assertThat(hexMessage,
                `is`("0x2f79826e437bfd4b709b8623f873664ee83a391ee3639a01abd41c49e4ce43f1"))
    }

    @Test
    fun signMessage() {
        val signedMessage = ethSigner.signMessage(
                createEtherTransaction(), SignExample.KEY_PAIR)
        val hexMessage = Hex.toHexString(signedMessage)
        assertThat(hexMessage,
                `is`("0xf85580010a840add5355887fffffffffffffff80"
                        + "1c"
                        + "a046360b50498ddf5566551ce1ce69c46c565f1f478bb0ee680caf31fbc08ab727"
                        + "a01b2f1432de16d110407d544f519fc91b84c8e16d3b6ec899592d486a94974cd0"))
    }

    @Test
    fun etherTransactionAsRlpValues() {
        val transaction = createEtherTransaction()
        val signatureData =Sign.SignatureData(0.toByte(), ByteArray(32), ByteArray(32))
        val rlpStrings = ethSigner.asRlpValues(transaction, signatureData)
        assertThat(rlpStrings.size, `is`(9))
        assertThat(rlpStrings[3], equalTo(RlpString.create(BigInteger("add5355", 16)) as RlpType))
    }

    @Test
    fun contractAsRlpValues() {
        val rlpStrings = ethSigner.asRlpValues(
                createContractTransaction())
        assertThat(rlpStrings.size, `is`(6))
        assertThat(rlpStrings[3], `is`(RlpString.create("") as RlpType))
    }

//    @Test
//    fun eip155Encode() {
//        assertThat(ethSigner.encode(createEip155RawTransaction(), 1.toByte()),
//                `is`(Hex.hexStringToByteArray(
//                        "0xec098504a817c800825208943535353535353535353535353535353535353535880de0b6b3a764000080018080")))
//    }

    @Test
    fun eip155Transaction() {
        // https://github.com/ethereum/EIPs/issues/155
        val keyString = "0x4646464646464646464646464646464646464646464646464646464646464646"
        val keyPair = ECKeyPair.create(keyString)

        assertThat(ethSigner.signMessage(
                createEip155RawTransaction(), keyPair, 1.toByte()),
                `is`(Hex.hexStringToByteArray(
                        "0xf86c098504a817c800825208943535353535353535353535353535353535353535880"
                                + "de0b6b3a76400008025a028ef61340bd939bc2195fe537567866003e1a15d"
                                + "3c71ff63e1590620aa636276a067cbe9d8997f761aecb703304b3800ccf55"
                                + "5c9f3dc64214b297fb1966a3b6d83")))
    }


    @Test
    @Throws(Exception::class)
    fun decoding() {
        val nonce = BigInteger.ZERO
        val gasPrice = BigInteger.ONE
        val gasLimit = BigInteger.TEN
        val to = "0x0add5355"
        val value = BigInteger.valueOf(java.lang.Long.MAX_VALUE)
        val rawTransaction = EthereumRawTransaction.createEtherTransaction(
                nonce, gasPrice, gasLimit, to, value)
        val encodedMessage = ethSigner.encode(rawTransaction)
        val hexMessage = Hex.toHexString(encodedMessage)

        val result = ethSigner.decode(hexMessage)
        assertNotNull(result)
        assertEquals(nonce, result.nonce)
        assertEquals(gasPrice, result.gasPrice)
        assertEquals(gasLimit, result.gasLimit)
        assertEquals(to, result.to)
        assertEquals(value, result.value)
        assertEquals("", result.data)
    }

    @Test
    @Throws(Exception::class)
    fun decodingSigned() {
        val nonce = BigInteger.ZERO
        val gasPrice = BigInteger.ONE
        val gasLimit = BigInteger.TEN
        val to = "0x0add5355"
        val value = BigInteger.valueOf(java.lang.Long.MAX_VALUE)
        val rawTransaction = EthereumRawTransaction.createEtherTransaction(
                nonce, gasPrice, gasLimit, to, value)
        val signedMessage = ethSigner.signMessage(
                rawTransaction, SignExample.KEY_PAIR)
        val hexMessage = Hex.toHexString(signedMessage)

        val result = ethSigner.decode(hexMessage)
        assertNotNull(result)
        assertEquals(nonce, result.nonce)
        assertEquals(gasPrice, result.gasPrice)
        assertEquals(gasLimit, result.gasLimit)
        assertEquals(to, result.to)
        assertEquals(value, result.value)
        assertEquals("", result.data)
        assertNotNull(result.signatureData)
        val signatureData = result.signatureData
        val encodedTransaction = ethSigner.encode(rawTransaction)
        val key= signTransaction.signedMessageToKey(encodedTransaction.keccak256, signatureData!!)
        assertEquals(key[Sign.UNCOMPRESS_INDEX], SignExample.PUBLIC_KEY)
        assertEquals(SignExample.ADDRESS, result.from)
        result.verify(SignExample.ADDRESS)
        assertNull(result.chainId)
    }

    @Test
    @Throws(Exception::class)
    fun decodingSignedChainId() {
        val nonce = BigInteger.ZERO
        val gasPrice = BigInteger.ONE
        val gasLimit = BigInteger.TEN
        val to = "0x0add5355"
        val value = BigInteger.valueOf(java.lang.Long.MAX_VALUE)
        val chainId = 1
        val rawTransaction = EthereumRawTransaction.createEtherTransaction(
                nonce, gasPrice, gasLimit, to, value)
        val signedMessage = ethSigner.signMessage(
                rawTransaction, SignExample.KEY_PAIR, chainId.toByte())
        val hexMessage = Hex.toHexString(signedMessage)

        val result = ethSigner.decode(hexMessage)
        assertNotNull(result)
        assertEquals(nonce, result.nonce)
        assertEquals(gasPrice, result.gasPrice)
        assertEquals(gasLimit, result.gasLimit)
        assertEquals(to, result.to)
        assertEquals(value, result.value)
        assertEquals("", result.data)
        assertEquals(SignExample.ADDRESS, result.from)
        result.verify(SignExample.ADDRESS)
        assertEquals(chainId, result.chainId)
    }

    @Test
    @Throws(Exception::class)
    fun rSize31() {
        //CHECKSTYLE:OFF
        val hexTransaction = "0xf883370183419ce09433c98f20dd73d7bb1d533c4aa3371f2b30c6ebde80a45093dc7d00000000000000000000000000000000000000000000000000000000000000351c9fb90996c836fb34b782ee3d6efa9e2c79a75b277c014e353b51b23b00524d2da07435ebebca627a51a863bf590aff911c4746ab8386a0477c8221bb89671a5d58"
        //CHECKSTYLE:ON
        val result = ethSigner.decode(hexTransaction)
        assertEquals("0x1b609b03E2e9B0275A61fA5c69A8f32550285536", result.from)
    }

    private fun createEtherTransaction(): EthereumRawTransaction {
        return EthereumRawTransaction.createEtherTransaction(
                nonce = BigInteger.ZERO,
                gasPrice = BigInteger.ONE,
                gasLimit = BigInteger.TEN,
                to = "0xadd5355",
                value = BigInteger.valueOf(java.lang.Long.MAX_VALUE))
    }

    private fun createContractTransaction(): EthereumRawTransaction {
        return EthereumRawTransaction.createContractTransaction(
                nonce = BigInteger.ZERO,
                gasPrice = BigInteger.ONE,
                gasLimit = BigInteger.TEN,
                value = BigInteger.valueOf(java.lang.Long.MAX_VALUE),
                init = "01234566789")
    }

    private fun createEip155RawTransaction(): EthereumRawTransaction {
        return EthereumRawTransaction.createEtherTransaction(
                nonce = BigInteger.valueOf(9),
                gasPrice = BigInteger.valueOf(20000000000L),
                gasLimit = BigInteger.valueOf(21000),
                to = "0x3535353535353535353535353535353535353535",
                value = BigInteger.valueOf(1000000000000000000L))
    }
}
