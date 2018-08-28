package com.dcen.wallet.dellet.core.bip39.wordlists

import util.WordListHashing.WORD_COUNT
import org.junit.Assert.assertEquals
import org.junit.Test
import util.WordListHashing

class EnglishListContentTests {

    private val wordList = English.INSTANCE

    @Test
    fun hashCheck() {
        assertEquals("ffbc2f3228ee610ad011ff9d38a1fb8e49e23fb60601aa7605733abb0005b01e",
                WordListHashing.hashWordList(wordList))
    }

    @Test
    fun normalizedHashCheck() {
        assertEquals("ffbc2f3228ee610ad011ff9d38a1fb8e49e23fb60601aa7605733abb0005b01e",
                WordListHashing.hashWordListNormalized(wordList))
    }

    @Test(expected = ArrayIndexOutOfBoundsException::class)
    fun correctNumberOfWords() {
        wordList.getWord(WORD_COUNT + 1)
    }
}