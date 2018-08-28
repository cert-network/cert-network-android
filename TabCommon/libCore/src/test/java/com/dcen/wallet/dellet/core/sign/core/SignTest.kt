package com.dcen.wallet.dellet.core.sign.core

import com.dcen.wallet.dellet.core.sign.SignExample
import com.dcen.wallet.dellet.core.util.Hex
import com.dcen.wallet.dellet.core.util.keccak256
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import java.math.BigInteger
import java.security.SignatureException

class SignTest {

    companion object {
        private val TEST_MESSAGE = "A test message".toByteArray()
    }

    val sign: Sign = Sign()

    @Test
    fun signMessage() {
        val signatureData = sign.signMessage(TEST_MESSAGE.keccak256, SignExample.KEY_PAIR)

        val expected = Sign.SignatureData(
                27.toByte(),
                Hex.hexStringToByteArray("0x9631f6d21dec448a213585a4a41a28ef3d4337548aa34734478b563036163786"),
                Hex.hexStringToByteArray("0x2ff816ee6bbb82719e983ecd8a33a4b45d32a4b58377ef1381163d75eedc900b")
        )

        assertThat<Sign.SignatureData>(signatureData, `is`(expected))
    }

    @Test
    @Throws(SignatureException::class)
    fun signedMessageToKey() {
        val signatureData = sign.signMessage(TEST_MESSAGE, SignExample.KEY_PAIR)
        val key = sign.signedMessageToKey(TEST_MESSAGE, signatureData)
        assertThat<BigInteger>(key[Sign.UNCOMPRESS_INDEX], equalTo(SignExample.PUBLIC_KEY))
    }


    @Test(expected = RuntimeException::class)
    @Throws(SignatureException::class)
    fun invalidSignature() {
        sign.signedMessageToKey(
                TEST_MESSAGE, Sign.SignatureData(27.toByte(), byteArrayOf(1), byteArrayOf(0)))
    }

}
