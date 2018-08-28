package com.dcen.wallet.dellet.core.bip39

import util.TestCharSequence.preventToStringAndSubSequence
import com.dcen.wallet.dellet.core.bip39.wordlists.English
import com.dcen.wallet.dellet.core.bip39.wordlists.French
import com.dcen.wallet.dellet.core.bip39.wordlists.Japanese
import com.dcen.wallet.dellet.core.bip39.wordlists.Spanish
import org.junit.Assert.*
import org.junit.Test
import java.text.Normalizer
import java.util.*

class WordListMapNormalizationTests {

    @Test
    fun given_WordList_and_get_normalized_form_returns_same_instance_twice() {
        val word = Japanese.INSTANCE.getWord(2)
        assertWordIsNotNormalized(word)
        val map = WordListMapNormalization(Japanese.INSTANCE)
        val word1 = map.normalize(word)
        val word2 = map.normalize(word)
        assertWordIsNormalized(word1)
        assertSame(word1, word2)
    }

    @Test
    fun all_words_in_WordList_are_cached() {
        val map = WordListMapNormalization(Japanese.INSTANCE)
        for (i in 0..2047) {
            val word = Japanese.INSTANCE.getWord(i)
            val word1 = map.normalize(word)
            val word2 = map.normalize(word)
            assertWordIsNormalized(word1)
            assertSame(word1, word2)
        }
    }

    @Test
    fun all_normalized_words_in_WordList_are_cached() {
        val map = WordListMapNormalization(Japanese.INSTANCE)
        for (i in 0..2047) {
            val word = map.normalize(Japanese.INSTANCE.getWord(i))
            val word1 = map.normalize(word)
            val word2 = map.normalize(word)
            assertWordIsNormalized(word1)
            assertSame(word1, word2)
        }
    }

    @Test
    fun all_un_normalized_words_in_WordList_are_cached() {
        for (wordList in Arrays.asList<WordList>(Japanese.INSTANCE, English.INSTANCE, French.INSTANCE, Spanish.INSTANCE)) {
            val map = WordListMapNormalization(wordList)
            for (i in 0..2047) {
                val originalWord = wordList.getWord(i)
                val nfcWord = Normalizer.normalize(originalWord, Normalizer.Form.NFC)
                val nfkcWord = Normalizer.normalize(originalWord, Normalizer.Form.NFKC)
                val nfkdWord = Normalizer.normalize(originalWord, Normalizer.Form.NFKD)
                val word1 = map.normalize(nfcWord)
                val word2 = map.normalize(nfkcWord)
                val word3 = map.normalize(nfkdWord)
                assertWordIsNormalized(word1)
                assertSame(word1, word2)
                assertSame(word1, word3)
            }
        }
    }

    @Test
    fun English_returns_same_word() {
        val map = WordListMapNormalization(English.INSTANCE)
        for (i in 0..2047) {
            val word = English.INSTANCE.getWord(i)
            val word1 = map.normalize(word)
            assertWordIsNormalized(word1)
            assertSame(word1, word)
        }
    }

    @Test
    fun given_WordList_and_get_normalized_form_of_word_off_WordList_returns_different_instances() {
        val word = Japanese.INSTANCE.getWord(2) + "X"
        assertWordIsNotNormalized(word)
        val map = WordListMapNormalization(Japanese.INSTANCE)
        val word1 = map.normalize(word)
        val word2 = map.normalize(word)
        assertWordIsNormalized(word1)
        assertWordIsNormalized(word2)
        assertNotSame(word1, word2)
        assertEquals(word1, Normalizer.normalize(word, Normalizer.Form.NFKD))
    }

    @Test
    fun does_not_call_to_string_when_in_the_dictionary() {
        val map = WordListMapNormalization(Japanese.INSTANCE)
        val word = Japanese.INSTANCE.getWord(51)
        assertWordIsNotNormalized(word)
        val wordAsSecureSequence = preventToStringAndSubSequence(word)
        val word1 = map.normalize(wordAsSecureSequence)
        assertWordIsNormalized(word1)
        val word2 = map.normalize(wordAsSecureSequence)
        assertSame(word1, word2)
    }

    /**
     * This works because the split creates char sequences with 0 hashcode
     */
    @Test
    fun a_fresh_char_sequence_from_a_split_still_does_not_need_to_to_string() {
        val map = WordListMapNormalization(Japanese.INSTANCE)
        val word2 = Japanese.INSTANCE.getWord(2)
        val word51 = Japanese.INSTANCE.getWord(51)
        val sentence = word2 + Japanese.INSTANCE.getSpace() + word51
        val split = CharSequenceSplitter(' ', Japanese.INSTANCE.getSpace()).split(sentence)
        assertNotSame(split[0], word2)
        assertNotSame(split[1], word51)
        assertSame(map.normalize(word2), map.normalize(split[0]))
        assertSame(map.normalize(word51), map.normalize(split[1]))
        assertSame(map.normalize(word2), map.normalize(preventToStringAndSubSequence(split[0])))
        assertSame(map.normalize(word51), map.normalize(preventToStringAndSubSequence(split[1])))
    }

    private fun assertWordIsNotNormalized(word: String) {
        assertFalse(isNormalized(word))
    }

    private fun assertWordIsNormalized(word: String) {
        assertTrue(isNormalized(word))
    }

    private fun isNormalized(word: String): Boolean {
        return Normalizer.isNormalized(word, Normalizer.Form.NFKD)
    }
}