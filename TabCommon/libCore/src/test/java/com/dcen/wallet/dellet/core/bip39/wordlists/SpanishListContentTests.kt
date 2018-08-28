package com.dcen.wallet.dellet.core.bip39.wordlists

import org.junit.Assert.assertEquals
import org.junit.Test
import util.WordListHashing
import util.WordListHashing.WORD_COUNT

class SpanishListContentTests {

    private val wordList = Spanish.INSTANCE

    @Test
    fun hashCheck() {
        assertEquals("134e8bfaf106863a7f10c04bdf922e15bbce43c30c6558c8537199d7c09ea0b2",
                WordListHashing.hashWordList(wordList))
    }

    @Test
    fun normalizedHashCheck() {
        assertEquals("134e8bfaf106863a7f10c04bdf922e15bbce43c30c6558c8537199d7c09ea0b2",
                WordListHashing.hashWordListNormalized(wordList))
    }

    @Test(expected = ArrayIndexOutOfBoundsException::class)
    fun correctNumberOfWords() {
        wordList.getWord(WORD_COUNT + 1)
    }
}