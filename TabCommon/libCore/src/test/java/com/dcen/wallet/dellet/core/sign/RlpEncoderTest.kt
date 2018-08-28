package com.dcen.wallet.dellet.core.sign

import com.dcen.wallet.dellet.core.sign.ethereum.rlp.RlpEncoder
import com.dcen.wallet.dellet.core.sign.ethereum.rlp.RlpList
import com.dcen.wallet.dellet.core.sign.ethereum.rlp.RlpString
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import java.math.BigInteger
import java.util.*

class RlpEncoderTest {

    /**
     * Examples taken from https://github.com/ethereum/wiki/wiki/RLP#examples.
     *
     *
     * For further examples see https://github.com/ethereum/tests/tree/develop/RLPTests.
     */
    @Test
    fun testEncode() {
        assertThat(RlpEncoder.encode(RlpString.create("dog")),
                `is`(byteArrayOf(0x83.toByte(), 'd'.toByte(), 'o'.toByte(), 'g'.toByte())))

        assertThat(RlpEncoder.encode(RlpList(RlpString.create("cat"), RlpString.create("dog"))),
                `is`(byteArrayOf(0xc8.toByte(), 0x83.toByte(), 'c'.toByte(), 'a'.toByte(), 't'.toByte(), 0x83.toByte(), 'd'.toByte(), 'o'.toByte(), 'g'.toByte())))

        assertThat(RlpEncoder.encode(RlpString.create("")),
                `is`(byteArrayOf(0x80.toByte())))

        assertThat(RlpEncoder.encode(RlpString.create(byteArrayOf())),
                `is`(byteArrayOf(0x80.toByte())))

        assertThat(RlpEncoder.encode(RlpList()),
                `is`(byteArrayOf(0xc0.toByte())))

        assertThat(RlpEncoder.encode(RlpString.create(BigInteger.valueOf(0x0f))),
                `is`(byteArrayOf(0x0f.toByte())))

        assertThat(RlpEncoder.encode(RlpString.create(BigInteger.valueOf(0x0400))),
                `is`(byteArrayOf(0x82.toByte(), 0x04.toByte(), 0x00.toByte())))

        assertThat(RlpEncoder.encode(RlpList(
                RlpList(),
                RlpList(RlpList()),
                RlpList(RlpList(), RlpList(RlpList())))),
                `is`(byteArrayOf(0xc7.toByte(), 0xc0.toByte(), 0xc1.toByte(), 0xc0.toByte(), 0xc3.toByte(), 0xc0.toByte(), 0xc1.toByte(), 0xc0.toByte())))

        assertThat(RlpEncoder.encode(
                RlpString.create("Lorem ipsum dolor sit amet, consectetur adipisicing elit")),
                `is`(byteArrayOf(0xb8.toByte(), 0x38.toByte(), 'L'.toByte(), 'o'.toByte(), 'r'.toByte(), 'e'.toByte(), 'm'.toByte(), ' '.toByte(), 'i'.toByte(), 'p'.toByte(), 's'.toByte(), 'u'.toByte(), 'm'.toByte(), ' '.toByte(), 'd'.toByte(), 'o'.toByte(), 'l'.toByte(), 'o'.toByte(), 'r'.toByte(), ' '.toByte(), 's'.toByte(), 'i'.toByte(), 't'.toByte(), ' '.toByte(), 'a'.toByte(), 'm'.toByte(), 'e'.toByte(), 't'.toByte(), ','.toByte(), ' '.toByte(), 'c'.toByte(), 'o'.toByte(), 'n'.toByte(), 's'.toByte(), 'e'.toByte(), 'c'.toByte(), 't'.toByte(), 'e'.toByte(), 't'.toByte(), 'u'.toByte(), 'r'.toByte(), ' '.toByte(), 'a'.toByte(), 'd'.toByte(), 'i'.toByte(), 'p'.toByte(), 'i'.toByte(), 's'.toByte(), 'i'.toByte(), 'c'.toByte(), 'i'.toByte(), 'n'.toByte(), 'g'.toByte(), ' '.toByte(), 'e'.toByte(), 'l'.toByte(), 'i'.toByte(), 't'.toByte())))

        assertThat(RlpEncoder.encode(RlpString.create(BigInteger.ZERO)),
                `is`(byteArrayOf(0x80.toByte())))

        // https://github.com/paritytech/parity/blob/master/util/rlp/tests/tests.rs#L239
        assertThat(RlpEncoder.encode(RlpString.create(byteArrayOf(0))),
                `is`(byteArrayOf(0x00.toByte())))

        assertThat(RlpEncoder.encode(
                RlpList(
                        RlpString.create("zw"),
                        RlpList(RlpString.create(4L)),
                        RlpString.create(1L))),
                `is`(byteArrayOf(0xc6.toByte(), 0x82.toByte(), 0x7a.toByte(), 0x77.toByte(), 0xc1.toByte(), 0x04.toByte(), 0x01.toByte())))

        // 55 bytes. See https://github.com/web3j/web3j/issues/519
        val encodeMe = ByteArray(55)
        Arrays.fill(encodeMe, 0.toByte())
        val expectedEncoding = ByteArray(56)
        expectedEncoding[0] = 0xb7.toByte()
        System.arraycopy(encodeMe, 0, expectedEncoding, 1, encodeMe.size)
        assertThat(RlpEncoder.encode(RlpString.create(encodeMe)),
                `is`(expectedEncoding))
    }
}
