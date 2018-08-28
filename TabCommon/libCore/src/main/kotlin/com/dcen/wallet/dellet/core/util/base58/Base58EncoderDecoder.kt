package com.dcen.wallet.dellet.core.util.base58

import com.dcen.wallet.dellet.core.util.base58.CapacityCalculator.maximumBase58StringLength
import java.util.*


internal class Base58EncoderDecoder(private val workingBuffer: WorkingBuffer) : GeneralEncoderDecoder {

    companion object {

        private val DIGITS = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray()
        private val VALUES = initValues(DIGITS)

        private fun initValues(alphabet: CharArray): IntArray {
            val lookup = IntArray('z'.toInt() + 1)
            Arrays.fill(lookup, -1)
            for (i in alphabet.indices)
                lookup[alphabet[i].toInt()] = i
            return lookup
        }

        private fun valueOf(base58Char: Char): Int {
            return if (base58Char.toInt() >= VALUES.size) -1 else VALUES[base58Char.toInt()]
        }
    }

    private val target = StringBuilderEncodeTarget()

    override
    fun encode(bytes: ByteArray): String {
        target.clear()
        encode(bytes, target, target)
        return target.toString()
    }

    override
    fun encode(bytes: ByteArray, target: EncodeTargetFromCapacity) {
        val characters = maximumBase58StringLength(bytes.size)
        encode(bytes, target.withCapacity(characters), characters)
    }

    override
    fun encode(bytes: ByteArray, setCapacity: EncodeTargetCapacity, target: EncodeTarget) {
        val characters = maximumBase58StringLength(bytes.size)
        setCapacity.setCapacity(characters)
        encode(bytes, target, characters)
    }

    override
    fun encode(bytes: ByteArray, target: EncodeTarget) {
        val characters = maximumBase58StringLength(bytes.size)
        encode(bytes, target, characters)
    }

    private fun encode(bytes: ByteArray, target: EncodeTarget, capacity: Int) {
        val a = DIGITS
        val bLen = bytes.size
        val d = getBufferOfAtLeastBytes(capacity)
        try {
            var dlen = -1
            var blanks = 0
            var j = 0
            for (i in 0 until bLen) {
                var c = bytes[i].toInt() and 0xff
                if (c == 0 && blanks == i) {
                    target.append(a[0])
                    blanks++
                }
                j = 0
                while (j <= dlen || c != 0) {
                    var n: Int
                    if (j > dlen) {
                        dlen = j
                        n = c
                    } else {
                        n = d[j].toInt()
                        n = (n shl 8) + c
                    }
                    d.put(j, (n % 58).toByte())
                    c = n / 58
                    j++
                }
            }
            while (j-- > 0) {
                target.append(a[d[j].toInt()])
            }
        } finally {
            d.clear()
        }
    }

    override
    fun decode(base58: CharSequence): ByteArray {
        val target = ByteArrayTarget()
        decode(base58, target)
        return target.asByteArray()
    }

    override
    fun decode(base58: CharSequence, target: DecodeTarget) {
        val strLen = base58.length
        val d = getBufferOfAtLeastBytes(strLen)
        try {
            var dlen = -1
            var blanks = 0
            var j = 0
            for (i in 0 until strLen) {
                j = 0
                val charAtI = base58[i]
                var c = valueOf(charAtI)
                if (c < 0) {
                    throw BadCharacterException(charAtI)
                }
                if (c == 0 && blanks == i) {
                    blanks++
                }
                while (j <= dlen || c != 0) {
                    var n: Int
                    if (j > dlen) {
                        dlen = j
                        n = c
                    } else {
                        n = d[j].toInt() and 0xff
                        n = n * 58 + c
                    }
                    d.put(j, n.toByte())
                    c = n.ushr(8)
                    j++
                }
            }
            val outputLength = j + blanks
            val writer = target.getWriterForLength(outputLength)
            for (i in 0 until blanks) {
                writer.append(0.toByte())
            }
            val end = outputLength - 1
            for (i in blanks until outputLength) {
                writer.append(d[end - i])
            }
        } finally {
            d.clear()
        }
    }

    private fun getBufferOfAtLeastBytes(atLeast: Int): WorkingBuffer {
        workingBuffer.setCapacity(atLeast)
        return workingBuffer
    }

}