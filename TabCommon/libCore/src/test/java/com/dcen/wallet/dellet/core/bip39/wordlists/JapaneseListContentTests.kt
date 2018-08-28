package com.dcen.wallet.dellet.core.bip39.wordlists

import org.junit.Assert.assertEquals
import org.junit.Test
import util.WordListHashing
import util.WordListHashing.WORD_COUNT

class JapaneseListContentTests {

    private val wordList = Japanese.INSTANCE

    @Test
    fun hashCheck() {
        assertEquals("2f61e05f096d93378a25071de9238ef2ce8d12d773a75640a3a881797e9e2148",
                WordListHashing.hashWordList(wordList))
    }

    @Test
    fun normalizedHashCheck() {
        assertEquals("b20ee3499703a2a0e02ba886edc61363ce380989a8212aaf1866e5bdc6b60c61",
                WordListHashing.hashWordListNormalized(wordList))
    }

    @Test(expected = ArrayIndexOutOfBoundsException::class)
    fun correctNumberOfWords() {
        wordList.getWord(WORD_COUNT + 1)
    }
}