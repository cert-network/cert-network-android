package com.dcen.wallet.dellet.core.bip39

import com.dcen.wallet.dellet.core.bip39.testjson.EnglishJson
import com.dcen.wallet.dellet.core.bip39.testjson.TestVectorJson
import com.dcen.wallet.dellet.core.bip39.validation.InvalidChecksumException
import com.dcen.wallet.dellet.core.bip39.validation.InvalidWordCountException
import com.dcen.wallet.dellet.core.bip39.validation.UnexpectedWhiteSpaceException
import com.dcen.wallet.dellet.core.bip39.validation.WordNotFoundException
import com.dcen.wallet.dellet.core.bip39.wordlists.English
import com.dcen.wallet.dellet.core.bip39.wordlists.French
import com.dcen.wallet.dellet.core.bip39.wordlists.Japanese
import com.dcen.wallet.dellet.core.bip39.wordlists.Spanish
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import util.TestCharSequence.preventToString
import util.TestCharSequence.preventToStringAndSubSequence
import java.util.*
import java.util.stream.Collectors

@RunWith(Parameterized::class)
class MnemonicValidationTests(private val mode: Mode) {

    enum class Mode {
        ValidateWholeString,
        ValidateAsStringList
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return Arrays.asList(arrayOf<Any>(Mode.ValidateWholeString), arrayOf<Any>(Mode.ValidateAsStringList))
        }
    }


    @Test(expected = WordNotFoundException::class)
    @Throws(Exception::class)
    fun bad_english_word() {
        try {
            validateExpectingBadWord(
                    "abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon alan",
                    English.INSTANCE)
        } catch (e: WordNotFoundException) {
            assertEquals("Word not found in word list \"alan\", suggestions \"aisle\", \"alarm\"", e.message)
            assertEquals("alan", e.word)
            assertEquals("aisle", e.suggestion1)
            assertEquals("alarm", e.suggestion2)
            throw e
        }

    }

    @Test(expected = WordNotFoundException::class)
    @Throws(Exception::class)
    fun word_too_short() {
        try {
            validateExpectingBadWord(
                    "aero abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon alan",
                    English.INSTANCE)
        } catch (e: WordNotFoundException) {
            assertEquals("Word not found in word list \"aero\", suggestions \"advice\", \"aerobic\"", e.message)
            assertEquals("aero", e.word)
            assertEquals("advice", e.suggestion1)
            assertEquals("aerobic", e.suggestion2)
            throw e
        }

    }

    @Test(expected = WordNotFoundException::class)
    @Throws(Exception::class)
    fun bad_english_word_alphabetically_before_all_others() {
        try {
            validateExpectingBadWord(
                    "aardvark abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon alan",
                    English.INSTANCE)
        } catch (e: WordNotFoundException) {
            assertEquals("Word not found in word list \"aardvark\", suggestions \"abandon\", \"ability\"", e.message)
            assertEquals("aardvark", e.word)
            assertEquals("abandon", e.suggestion1)
            assertEquals("ability", e.suggestion2)
            throw e
        }

    }

    @Test(expected = WordNotFoundException::class)
    @Throws(Exception::class)
    fun bad_english_word_alphabetically_after_all_others() {
        try {
            validateExpectingBadWord(
                    "zymurgy abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon alan",
                    English.INSTANCE)
        } catch (e: WordNotFoundException) {
            assertEquals("Word not found in word list \"zymurgy\", suggestions \"zone\", \"zoo\"", e.message)
            assertEquals("zymurgy", e.word)
            assertEquals("zone", e.suggestion1)
            assertEquals("zoo", e.suggestion2)
            throw e
        }

    }

    @Test(expected = WordNotFoundException::class)
    @Throws(Exception::class)
    fun bad_japanese_word() {
        try {
            validateExpectingBadWord(
                    "そつう　れきだ　ほんやく　わかす　りくつ　ばいか　ろせん　やちん　そつう　れきだい　ほんやく　わかめ",
                    Japanese.INSTANCE)
        } catch (e: WordNotFoundException) {
            assertEquals("Word not found in word list \"れきだ\", suggestions \"れきし\", \"れきだい\"", e.message)
            assertEquals("れきだ", e.word)
            assertEquals("れきし", e.suggestion1)
            assertEquals("れきだい", e.suggestion2)
            throw e
        }

    }

    @Test(expected = WordNotFoundException::class)
    @Throws(Exception::class)
    fun bad_japanese_word_normalized_behaviour() {
        try {
            validateExpectingBadWord(
                    "そつう　れきだ　ほんやく　わかす　りくつ　ばいか　ろせん　やちん　そつう　れきだい　ほんやく　わかめ",
                    Japanese.INSTANCE)
        } catch (e: WordNotFoundException) {
            assertEquals("Word not found in word list \"れきだ\", suggestions \"れきし\", \"れきだい\"", e.message)
            assertEquals("れきだ", e.word)
            assertEquals("れきし", e.suggestion1)
            assertEquals("れきだい", e.suggestion2)
            throw e
        }

    }

    @Test(expected = InvalidWordCountException::class)
    @Throws(Exception::class)
    fun eleven_words() {
        validate("abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon",
                English.INSTANCE)
    }

    @Test
    @Throws(Exception::class)
    fun all_english_test_vectors() {
        val data = EnglishJson.load()
        for (testCase in data.english) {
            assertTrue(validate(testCase[1], English.INSTANCE))
        }
    }

    @Test
    @Throws(Exception::class)
    fun all_english_test_vectors_words_swapped() {
        var testCaseCount = 0
        val data = EnglishJson.load()
        for (testCase in data.english) {
            val mnemonic = swapWords(testCase[1], 0, 1, English.INSTANCE)
            if (mnemonic == testCase[1]) continue //word were same
            assertFalse(validate(mnemonic, English.INSTANCE))
            testCaseCount++
        }
        assertEquals(18, testCaseCount.toLong())
    }

    @Test
    fun additional_space_end_English() {
        if (mode == Mode.ValidateAsStringList) return
        assertThatThrownBy {
            validate("abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon about ",
                    English.INSTANCE)
        }.isInstanceOf(UnexpectedWhiteSpaceException::class.java)
    }

    @Test
    fun additional_space_start_English() {
        assertThatThrownBy {
            validate(" abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon about",
                    English.INSTANCE)
        }.isInstanceOf(UnexpectedWhiteSpaceException::class.java)
    }

    @Test
    fun additional_space_middle_English() {
        assertThatThrownBy {
            validate("abandon  abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon about",
                    English.INSTANCE)
        }.isInstanceOf(UnexpectedWhiteSpaceException::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun normalize_Japanese() {
        assertTrue(validate("あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あおぞら",
                Japanese.INSTANCE))
    }

    @Test
    @Throws(Exception::class)
    fun normalize_Japanese_2() {
        assertTrue(validate("あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あおぞら",
                Japanese.INSTANCE))
    }

    @Test
    @Throws(Exception::class)
    fun normalize_Japanese_regular_spaces() {
        assertTrue(validate("あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あおぞら",
                Japanese.INSTANCE))
    }

    @Test
    @Throws(Exception::class)
    fun all_japanese_test_vectors() {
        val data = TestVectorJson.loadJapanese()
        for (testVector in data.vectors) {
            assertTrue(validate(testVector.mnemonic, Japanese.INSTANCE))
        }
    }

    @Test
    @Throws(Exception::class)
    fun all_french_test_vectors() {
        val data = TestVectorJson.loadFrench()
        for (testVector in data.vectors) {
            assertTrue(validate(testVector.mnemonic, French.INSTANCE))
        }
    }

    @Test
    @Throws(Exception::class)
    fun all_spanish_test_vectors() {
        val data = TestVectorJson.loadSpanish()
        for (testVector in data.vectors) {
            assertTrue(validate(testVector.mnemonic, Spanish.INSTANCE))
        }
    }

    @Test
    @Throws(Exception::class)
    fun all_japanese_test_vectors_words_swapped() {
        var testCaseCount = 0
        val data = TestVectorJson.loadJapanese()
        for (testVector in data.vectors) {
            val mnemonic = swapWords(testVector.mnemonic, 1, 3, Japanese.INSTANCE)
            if (mnemonic == testVector.mnemonic) continue //word were same
            assertFalse(validate(mnemonic, Japanese.INSTANCE))
            testCaseCount++
        }
        assertEquals(18, testCaseCount.toLong())
    }

    private enum class ValidateMode {
        NOT_EXPECTING_BAD_WORD,
        EXPECTING_BAD_WORD
    }

    @Throws(InvalidWordCountException::class, WordNotFoundException::class, UnexpectedWhiteSpaceException::class)
    private fun validateExpectingBadWord(mnemonic: String, wordList: WordList): Boolean {
        return validate(mnemonic, wordList, ValidateMode.EXPECTING_BAD_WORD)
    }


    @Throws(InvalidWordCountException::class, WordNotFoundException::class, UnexpectedWhiteSpaceException::class)
    private fun validate(mnemonic: String, wordList: WordList, validateMode: ValidateMode = ValidateMode.NOT_EXPECTING_BAD_WORD): Boolean {
        try {
            when (mode) {
                MnemonicValidationTests.Mode.ValidateWholeString -> {
                    MnemonicValidator
                            .ofWordList(wordList)
                            .validate(if (validateMode == ValidateMode.EXPECTING_BAD_WORD)
                                mnemonic
                            else
                                preventToString(mnemonic))
                }
                MnemonicValidationTests.Mode.ValidateAsStringList -> {
                    val words: MutableList<CharSequence> =
                            Arrays.stream(mnemonic.split("[ \u3000]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                                    .map<CharSequence> { sequence ->
                                        if (validateMode == ValidateMode.EXPECTING_BAD_WORD)
                                            sequence
                                        else
                                            preventToStringAndSubSequence(sequence)
                                    }
                                    .collect(Collectors.toList())

                    MnemonicValidator
                            .ofWordList(wordList)
                            .validate(words)
                }
            }
            return true
        } catch (e: InvalidChecksumException) {
            return false
        }

    }


    private fun swapWords(mnemonic: String, index1: Int, index2: Int, wordList: WordList): String {
        val split = mnemonic.split(wordList.getSpace().toString().toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val temp = split[index1]
        split[index1] = split[index2]
        split[index2] = temp
        val joiner = StringJoiner(wordList.getSpace().toString())
        for (string in split) {
            joiner.add(string)
        }
        return joiner.toString()
    }

}