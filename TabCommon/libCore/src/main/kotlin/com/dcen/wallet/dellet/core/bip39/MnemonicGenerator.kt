package com.dcen.wallet.dellet.core.bip39

import com.dcen.wallet.dellet.core.bip39.ByteUtils.next11Bits
import com.dcen.wallet.dellet.core.util.sha256
import java.util.*

/**
 * Generates mnemonics from entropy.
 *
 * Create a generator using the given word list.
 *
 * @param wordList A known ordered list of 2048 words to select from.
 */
object MnemonicGenerator {

    /**
     * Create a mnemonic from the word list given the entropy.
     *
     * @param entropyHex 128-256 bits of hex entropy, number of bits must also be divisible by 32
     * @param target     Where to write the mnemonic to
     */
    fun createMnemonic(
            wordList: WordList,
            entropyHex: CharSequence ): CharSequence {
        val length = entropyHex.length
        if (length % 2 == 1)
            throw RuntimeException("Length of hex chars must be divisible by 2")
        val entropy = ByteArray(length / 2)
        try {
            var i = 0
            var j = 0
            while (i < length) {
                entropy[j] = (parseHex(entropyHex[i]) shl 4 or parseHex(entropyHex[i + 1])).toByte()
                i += 2
                j++
            }
            return createMnemonic(wordList, entropy)
        } finally {
            Arrays.fill(entropy, 0.toByte())
        }
    }

    /**
     * Create a mnemonic from the word list given the entropy.
     *
     * @param entropy 128-256 bits of entropy, number of bits must also be divisible by 32
     * @param target  Where to write the mnemonic to
     */
    fun createMnemonic(wordList: WordList,
                       entropy: ByteArray ): CharSequence {
        val wordIndexes = wordIndexes(entropy)
        try {
            return createMnemonic(wordList, wordIndexes)
        } finally {
            Arrays.fill(wordIndexes, 0)
        }
    }

    fun firstByteOfSha256(entropy: ByteArray): Byte {
        val hash = entropy.sha256
        val firstByte = hash[0]
        Arrays.fill(hash, 0.toByte())
        return firstByte
    }

    private fun createMnemonic(
            wordList: WordList,
            wordIndexes: IntArray ): StringBuilder {
        val sb = StringBuilder()
        val space: String = wordList.getSpace().toString()
        for (i in wordIndexes.indices) {
            if (i > 0) sb.append(space)
            sb.append(wordList.getWord(wordIndexes[i]))
        }
        return sb
    }

    private fun wordIndexes(entropy: ByteArray): IntArray {
        val ent = entropy.size * 8
        entropyLengthPreChecks(ent)

        val entropyWithChecksum = Arrays.copyOf(entropy, entropy.size + 1)
        entropyWithChecksum[entropy.size] = firstByteOfSha256(entropy)

        //checksum length
        val cs = ent / 32
        //mnemonic length
        val ms = (ent + cs) / 11

        //get the indexes into the word list
        val wordIndexes = IntArray(ms)

        var i = 0
        var wi = 0
        while (wi < ms) {
            wordIndexes[wi] = next11Bits(entropyWithChecksum, i)
            i += 11
            wi++
        }
        return wordIndexes
    }


    private fun entropyLengthPreChecks(ent: Int) {
        if (ent < 128)
            throw RuntimeException("Entropy too low, 128-256 bits allowed")
        if (ent > 256)
            throw RuntimeException("Entropy too high, 128-256 bits allowed")
        if (ent % 32 > 0)
            throw RuntimeException("Number of entropy bits must be divisible by 32")
    }

    private fun parseHex(c: Char): Int {
        if (c in '0'..'9') return c - '0'
        if (c in 'a'..'f') return c - 'a' + 10
        if (c in 'A'..'F') return c - 'A' + 10
        throw RuntimeException("Invalid hex char '" + c + '\''.toString())
    }


}