package com.dcen.wallet.dellet.core.util

/**
 * Created by「 The Khaeng 」on 05 Jul 2018 :)
 */


fun Byte.toPositiveInt() = toInt() and 0xFF

infix fun Byte.shl(other: Byte): Byte = (toPositiveInt() shl other.toPositiveInt()).toByte()

infix fun Byte.shr(other: Byte): Byte = (toPositiveInt() shr other.toPositiveInt()).toByte()

infix fun Byte.and(other: Int): Int = (this.toPositiveInt() and other)
infix fun Byte.or(other: Int): Int = (this.toPositiveInt() or other)
infix fun Byte.xor(other: Int): Int = (this.toPositiveInt() xor other)
infix fun Byte.shl(other: Int): Int = (this.toPositiveInt() shl other)
infix fun Byte.shr(other: Int): Int = (this.toPositiveInt() shr other)

fun ByteArray.copy(): ByteArray = this.copyOfRange(0, this.size)

fun ByteArray.copyOfRange(start: Int, end: Int): ByteArray {
    val length = end - start
    val result = ByteArray(length)
    System.arraycopy(this, start, result, 0, length)
    return result
}

