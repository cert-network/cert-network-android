package com.dcen.wallet.dellet.core.sign

import com.dcen.wallet.dellet.core.sign.ethereum.rlp.RlpDecoder
import com.dcen.wallet.dellet.core.sign.ethereum.rlp.RlpList
import com.dcen.wallet.dellet.core.sign.ethereum.rlp.RlpString
import com.dcen.wallet.dellet.core.sign.ethereum.rlp.RlpType
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigInteger

class RlpDecoderTest {

    /**
     * Examples taken from https://github.com/ethereum/wiki/wiki/RLP#examples.
     * For further examples see https://github.com/ethereum/tests/tree/develop/RLPTests.
     */
    @Test
    fun testRLPDecode() {

        val value = 3000000000L
        assertThat(RlpString.create(value.toBigInteger()).toPositiveBigInteger().toLong(),
                equalTo(value))

        // empty array of binary
        assertTrue(RlpDecoder.decode(byteArrayOf()).values.isEmpty())

        // The string "dog" = [ 0x83, 'd', 'o', 'g' ]
        assertThat(RlpDecoder.decode(byteArrayOf(0x83.toByte(), 'd'.toByte(), 'o'.toByte(), 'g'.toByte())).values[0],
                `is`(RlpString.create("dog") as RlpType))

        // The list [ "cat", "dog" ] = [ 0xc8, 0x83, 'c', 'a', 't', 0x83, 'd', 'o', 'g' ]
        var rlpList = RlpDecoder.decode(
                byteArrayOf(0xc8.toByte(), 0x83.toByte(), 'c'.toByte(), 'a'.toByte(), 't'.toByte(), 0x83.toByte(), 'd'.toByte(), 'o'.toByte(), 'g'.toByte())).values[0] as RlpList

        assertThat(rlpList.values[0],
                `is`(RlpString.create("cat") as RlpType))

        assertThat(rlpList.values[1],
                `is`(RlpString.create("dog") as RlpType))

        // The empty string ('null') = [ 0x80 ]
        assertThat(RlpDecoder.decode(byteArrayOf(0x80.toByte())).values[0],
                `is`(RlpString.create("") as RlpType))

        assertThat(RlpDecoder.decode(byteArrayOf(0x80.toByte())).values[0],
                `is`(RlpString.create(byteArrayOf()) as RlpType))

        assertThat(RlpDecoder.decode(byteArrayOf(0x80.toByte())).values[0],
                `is`(RlpString.create(BigInteger.ZERO) as RlpType))

        // The empty list = [ 0xc0 ]
        assertThat<RlpType>(RlpDecoder.decode(byteArrayOf(0xc0.toByte())).values[0],
                instanceOf<Any>(RlpList::class.java))

        assertTrue((RlpDecoder.decode(byteArrayOf(0xc0.toByte()))
                .values[0] as RlpList).values.isEmpty())

        // The encoded integer 0 ('\x00') = [ 0x00 ]
        assertThat(RlpDecoder.decode(byteArrayOf(0x00.toByte())).values[0],
                `is`(RlpString.create(BigInteger.valueOf(0).toByte()) as RlpType))

        // The encoded integer 15 ('\x0f') = [ 0x0f ]
        assertThat(RlpDecoder.decode(byteArrayOf(0x0f.toByte())).values[0],
                `is`(RlpString.create(BigInteger.valueOf(15).toByte()) as RlpType))

        // The encoded integer 1024 ('\x04\x00') = [ 0x82, 0x04, 0x00 ]
        assertThat(RlpDecoder.decode(byteArrayOf(0x82.toByte(), 0x04.toByte(), 0x00.toByte()))
                .values[0],
                `is`(RlpString.create(BigInteger.valueOf(0x0400)) as RlpType))

        // The set theoretical representation of three,
        // [ [], [[]], [ [], [[]] ] ] = [ 0xc7, 0xc0, 0xc1, 0xc0, 0xc3, 0xc0, 0xc1, 0xc0 ]
        rlpList = RlpDecoder.decode(byteArrayOf(0xc7.toByte(), 0xc0.toByte(), 0xc1.toByte(), 0xc0.toByte(), 0xc3.toByte(), 0xc0.toByte(), 0xc1.toByte(), 0xc0.toByte()))
        assertThat(rlpList, instanceOf<Any>(RlpList::class.java))

        assertThat(rlpList.values.size, equalTo(1))

        assertThat<RlpType>(rlpList.values[0], instanceOf<Any>(RlpList::class.java))

        assertThat((rlpList.values[0] as RlpList).values.size,
                equalTo(3))

        assertThat<RlpType>((rlpList.values[0] as RlpList).values[0],
                instanceOf<Any>(RlpList::class.java))

        assertThat(((rlpList.values[0] as RlpList).values[0] as RlpList).values.size,
                equalTo(0))

        assertThat(((rlpList.values[0] as RlpList).values[1] as RlpList).values.size,
                equalTo(1))

        assertThat(((rlpList.values[0] as RlpList).values[2] as RlpList).values.size,
                equalTo(2))

        assertThat<RlpType>(((rlpList.values[0] as RlpList).values[2] as RlpList).values[0],
                instanceOf<Any>(RlpList::class.java))

        assertThat((((rlpList.values[0] as RlpList).values[2] as RlpList).values[0] as RlpList)
                .values.size,
                equalTo(0))

        assertThat((((rlpList.values[0] as RlpList).values[2] as RlpList).values[1] as RlpList)
                .values.size,
                equalTo(1))

        // The string "Lorem ipsum dolor sit amet,
        // consectetur adipisicing elit" =
        // [ 0xb8, 0x38, 'L', 'o', 'r', 'e', 'm', ' ', ... , 'e', 'l', 'i', 't' ]

        assertThat(RlpDecoder.decode(
                byteArrayOf(0xb8.toByte(), 0x38.toByte(), 'L'.toByte(), 'o'.toByte(), 'r'.toByte(), 'e'.toByte(), 'm'.toByte(), ' '.toByte(), 'i'.toByte(), 'p'.toByte(), 's'.toByte(), 'u'.toByte(), 'm'.toByte(), ' '.toByte(), 'd'.toByte(), 'o'.toByte(), 'l'.toByte(), 'o'.toByte(), 'r'.toByte(), ' '.toByte(), 's'.toByte(), 'i'.toByte(), 't'.toByte(), ' '.toByte(), 'a'.toByte(), 'm'.toByte(), 'e'.toByte(), 't'.toByte(), ','.toByte(), ' '.toByte(), 'c'.toByte(), 'o'.toByte(), 'n'.toByte(), 's'.toByte(), 'e'.toByte(), 'c'.toByte(), 't'.toByte(), 'e'.toByte(), 't'.toByte(), 'u'.toByte(), 'r'.toByte(), ' '.toByte(), 'a'.toByte(), 'd'.toByte(), 'i'.toByte(), 'p'.toByte(), 'i'.toByte(), 's'.toByte(), 'i'.toByte(), 'c'.toByte(), 'i'.toByte(), 'n'.toByte(), 'g'.toByte(), ' '.toByte(), 'e'.toByte(), 'l'.toByte(), 'i'.toByte(), 't'.toByte())).values[0],
                `is`(RlpString.create("Lorem ipsum dolor sit amet, consectetur adipisicing elit") as RlpType))

        // https://github.com/paritytech/parity/blob/master/util/rlp/tests/tests.rs#L239
        assertThat(RlpDecoder.decode(byteArrayOf(0x00.toByte())).values[0],
                `is`(RlpString.create(byteArrayOf(0)) as RlpType))

        rlpList = RlpDecoder.decode(byteArrayOf(0xc6.toByte(), 0x82.toByte(), 0x7a.toByte(), 0x77.toByte(), 0xc1.toByte(), 0x04.toByte(), 0x01.toByte()))

        assertThat((rlpList.values[0] as RlpList).values.size,
                equalTo(3))

        assertThat<RlpType>((rlpList.values[0] as RlpList).values[0],
                instanceOf<Any>(RlpString::class.java))

        assertThat<RlpType>((rlpList.values[0] as RlpList).values[1],
                instanceOf<Any>(RlpList::class.java))

        assertThat<RlpType>((rlpList.values[0] as RlpList).values[2],
                instanceOf<Any>(RlpString::class.java))

        assertThat((rlpList.values[0] as RlpList).values[0],
                `is`(RlpString.create("zw") as RlpType))

        assertThat(((rlpList.values[0] as RlpList).values[1] as RlpList).values[0],
                `is`(RlpString.create(4L) as RlpType))

        assertThat((rlpList.values[0] as RlpList).values[2],
                `is`(RlpString.create(1L) as RlpType))

        // payload more than 55 bytes
        val data = ("F86E12F86B80881BC16D674EC8000094CD2A3D9F938E13CD947EC05ABC7FE734D"
                + "F8DD8268609184E72A00064801BA0C52C114D4F5A3BA904A9B3036E5E118FE0DBB987"
                + "FE3955DA20F2CD8F6C21AB9CA06BA4C2874299A55AD947DBC98A25EE895AABF6B625C"
                + "26C435E84BFD70EDF2F69")

        val payload = hexStringToByteArray(data)
        rlpList = RlpDecoder.decode(payload)

        assertThat((rlpList.values[0] as RlpList).values.size,
                equalTo(2))

        assertThat<RlpType>((rlpList.values[0] as RlpList).values[0],
                instanceOf<Any>(RlpString::class.java))

        assertThat<RlpType>((rlpList.values[0] as RlpList).values[1],
                instanceOf<Any>(RlpList::class.java))

        assertThat(((rlpList.values[0] as RlpList).values[1] as RlpList).values.size,
                equalTo(9))

    }

    private fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        val data = ByteArray(len / 2)

        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(s[i], 16) shl 4) + Character.digit(s[i + 1], 16)).toByte()
            i += 2
        }

        return data
    }
}
