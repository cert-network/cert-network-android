package com.dcen.wallet.dellet.core.bip39

import com.dcen.wallet.dellet.core.bip39.ByteUtils.next11Bits
import org.junit.Assert.assertEquals
import org.junit.Test


class ByteUtilTests {


    @Test
    fun take11Bits() {
        val bytes = byteArrayOf(255.toByte(), 239.toByte(), 103, 0)
        assertEquals(2047, next11Bits(bytes, 0).toLong())
        assertEquals(2046, next11Bits(bytes, 1).toLong())
        assertEquals(1915, next11Bits(bytes, 8).toLong())
        assertEquals(1782, next11Bits(bytes, 9).toLong())
        assertEquals(1516, next11Bits(bytes, 10).toLong())
        assertEquals(985, next11Bits(bytes, 11).toLong())
        assertEquals(824, next11Bits(bytes, 16).toLong())
    }

    @Test
    fun take11Bits7F() {
        val bytes = byteArrayOf(0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f, 0x7f)
        assertEquals(1019, next11Bits(bytes, 0).toLong())
        assertEquals(2039, next11Bits(bytes, 1).toLong())
        assertEquals(2031, next11Bits(bytes, 2).toLong())
        assertEquals(2015, next11Bits(bytes, 3).toLong())
        assertEquals(1983, next11Bits(bytes, 4).toLong())
        assertEquals(1919, next11Bits(bytes, 5).toLong())
        assertEquals(1790, next11Bits(bytes, 6).toLong())
        assertEquals(1533, next11Bits(bytes, 7).toLong())
        assertEquals(1019, next11Bits(bytes, 8).toLong())
        assertEquals(2039, next11Bits(bytes, 9).toLong())
    }

    @Test
    fun take11Bits80() {
        val bytes = byteArrayOf(0x80.toByte(), 0x80.toByte(), 0x80.toByte(), 0x80.toByte())
        assertEquals(1028, next11Bits(bytes, 0).toLong())
        assertEquals(8, next11Bits(bytes, 1).toLong())
        assertEquals(16, next11Bits(bytes, 2).toLong())
        assertEquals(32, next11Bits(bytes, 3).toLong())
        assertEquals(64, next11Bits(bytes, 4).toLong())
        assertEquals(128, next11Bits(bytes, 5).toLong())
        assertEquals(257, next11Bits(bytes, 6).toLong())
        assertEquals(514, next11Bits(bytes, 7).toLong())
        assertEquals(1028, next11Bits(bytes, 8).toLong())
        assertEquals(8, next11Bits(bytes, 9).toLong())
    }
}
