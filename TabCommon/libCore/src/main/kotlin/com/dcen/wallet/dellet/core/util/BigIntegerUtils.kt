@file:JvmName("BigIntegerUtils")

package com.dcen.wallet.dellet.core.util

import org.bouncycastle.pqc.math.linearalgebra.ByteUtils
import java.math.BigInteger
import java.util.*

val ByteArray.parse256: BigInteger get() = BigInteger(1, this)

fun ByteArray.subArray(start: Int, end: Int): ByteArray = ByteUtils.subArray(this,start,end)


fun ByteArray.ser256(integer: BigInteger) {
    if (integer.bitLength() > this.size * 8)
        throw RuntimeException("ser256 failed, cannot fit integer in buffer")
    val modArr = integer.toByteArray()
    Arrays.fill(this, 0.toByte())
    copyTail(modArr, this)
    Arrays.fill(modArr, 0.toByte())
}

private fun copyTail(src: ByteArray, dest: ByteArray) {
    if (src.size < dest.size) {
        System.arraycopy(src, 0, dest, dest.size - src.size, src.size)
    } else {
        System.arraycopy(src, src.size - dest.size, dest, 0, dest.size)
    }
}