package com.dcen.wallet.dellet.core.bip39

import com.dcen.wallet.dellet.core.bip39.wordlists.English
import org.junit.Assert.assertEquals
import org.junit.Test

class MnemonicGenerationWordCountTests {

    @Test
    @Throws(Exception::class)
    fun twelveWordsBitLength() {
        assertEquals(128, Words.TWELVE.bitLength().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun twelveWords() {
        assertEquals(12, countWords(Words.TWELVE).toLong())
    }

    @Test
    @Throws(Exception::class)
    fun fifteenWordsBitLength() {
        assertEquals(160, Words.FIFTEEN.bitLength().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun fifteenWords() {
        assertEquals(15, countWords(Words.FIFTEEN).toLong())
    }

    @Test
    @Throws(Exception::class)
    fun eighteenWordsBitLength() {
        assertEquals(192, Words.EIGHTEEN.bitLength().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun eighteenWords() {
        assertEquals(18, countWords(Words.EIGHTEEN).toLong())
    }

    @Test
    @Throws(Exception::class)
    fun twentyOneWordsBitLength() {
        assertEquals(224, Words.TWENTY_ONE.bitLength().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun twentyOneWords() {
        assertEquals(21, countWords(Words.TWENTY_ONE).toLong())
    }

    @Test
    @Throws(Exception::class)
    fun twentyFourWordsBitLength() {
        assertEquals(256, Words.TWENTY_FOUR.bitLength().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun twentyFourWords() {
        assertEquals(24, countWords(Words.TWENTY_FOUR).toLong())
    }

    private fun countWords(words: Words): Int {
        return createMnemonic(ByteArray(words.byteLength()), English.INSTANCE)
                .split(("" + English.INSTANCE.getSpace()).toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size
    }

    private fun createMnemonic(f: ByteArray, wordList: WordList): String {
        return MnemonicGenerator
                .createMnemonic(wordList, f)
                .toString()
    }
}