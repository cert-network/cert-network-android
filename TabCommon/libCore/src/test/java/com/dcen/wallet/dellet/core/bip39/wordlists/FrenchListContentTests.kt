package com.dcen.wallet.dellet.core.bip39.wordlists


import org.junit.Assert.assertEquals
import org.junit.Test
import util.WordListHashing
import util.WordListHashing.WORD_COUNT

class FrenchListContentTests {
    private val wordList = French.INSTANCE

    @Test
    fun hashCheck() {
        assertEquals("9e515b24c9bb0119eaf18acf85a8303c4b8fec82dac53ad688e20f379de1286c",
                WordListHashing.hashWordList(wordList))
    }

    @Test
    fun normalizedHashCheck() {
        assertEquals("922939bd934c6128a897ad299de471bd7aafe578d28a37370e881dc998903d51",
                WordListHashing.hashWordListNormalized(wordList))
    }

    @Test(expected = ArrayIndexOutOfBoundsException::class)
    fun correctNumberOfWords() {
        wordList.getWord(WORD_COUNT + 1)
    }
}