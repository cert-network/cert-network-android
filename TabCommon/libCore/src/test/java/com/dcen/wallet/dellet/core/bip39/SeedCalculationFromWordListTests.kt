package com.dcen.wallet.dellet.core.bip39


import util.Hex.toHex
import util.TestCharSequence.preventToStringAndSubSequence
import com.dcen.wallet.dellet.core.bip39.testjson.EnglishJson
import com.dcen.wallet.dellet.core.bip39.testjson.TestVector
import com.dcen.wallet.dellet.core.bip39.testjson.TestVectorJson
import com.dcen.wallet.dellet.core.bip39.wordlists.English
import com.dcen.wallet.dellet.core.bip39.wordlists.French
import com.dcen.wallet.dellet.core.bip39.wordlists.Japanese
import com.dcen.wallet.dellet.core.bip39.wordlists.Spanish
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.Normalizer
import java.util.*
import java.util.stream.Collectors

class SeedCalculationFromWordListTests {

    @Test
    fun bip39_english() {
        assertEquals("2eea1e4d099089606b7678809be6090ccba0fca171d4ed42c550194ca8e3600cd1e5989dcca38e5f903f5c358c92e0dcaffc9e71a48ad489bb868025c907d1e1",
                calculateSeedHex("solar puppy hawk oxygen trip brief erase slot fossil mechanic filter voice", ""))
    }

    @Test
    fun bip39_english_word_not_found() {
        val mnemonicWithBadWord = "solar puppies hawk oxygen trip brief erase slot fossil mechanic filter voice"
        assertEquals(toHex(SeedCalculator().calculateSeed(mnemonicWithBadWord, "")),
                calculateSeedHex(mnemonicWithBadWord, "",
                        English.INSTANCE, ValidateMode.EXPECTING_BAD_WORD))
    }

    @Test
    fun bip39_non_normalized_Japanese_word_not_found() {
        val unNormalizedMnemonicWithBadWord = Normalizer.normalize("あおぞらAlan　あいこくしん　あいこくしん　あいこくしん", Normalizer.Form.NFC)
        assertEquals(toHex(SeedCalculator().calculateSeed(unNormalizedMnemonicWithBadWord, "")),
                calculateSeedHex(unNormalizedMnemonicWithBadWord, "",
                        Japanese.INSTANCE, ValidateMode.EXPECTING_BAD_WORD))
    }

    @Test
    fun bip39_english_with_passphrase() {
        assertEquals("36732d826f4fa483b5fe8373ef8d6aa3cb9c8fb30463d6c0063ee248afca2f87d11ebe6e75c2fb2736435994b868f8e9d4f4474c65ee05ac47aad7ef8a497846",
                calculateSeedHex("solar puppy hawk oxygen trip brief erase slot fossil mechanic filter voice", "CryptoIsCool"))
    }

    @Test
    fun all_english_test_vectors() {
        val data = EnglishJson.load()
        for (testCase in data.english!!) {
            assertEquals(testCase[2], calculateSeedHex(testCase[1], "TREZOR"))
        }
    }

    @Test
    fun passphrase_normalization() {
        assertEquals(calculateSeedHex("solar puppy hawk oxygen trip brief erase slot fossil mechanic filter voice", "ｶ"),
                calculateSeedHex("solar puppy hawk oxygen trip brief erase slot fossil mechanic filter voice", "カ"))
    }

    @Test
    fun normalize_Japanese() {
        assertEquals("646f1a38134c556e948e6daef213609a62915ef568edb07ffa6046c87638b4b140fef2e0c6d7233af640c4a63de6d1a293288058c8ac1d113255d0504e63f301",
                calculateSeedHex("あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あおぞら",
                        "",
                        Japanese.INSTANCE))
    }

    @Test
    fun normalize_Japanese_2() {
        assertEquals("646f1a38134c556e948e6daef213609a62915ef568edb07ffa6046c87638b4b140fef2e0c6d7233af640c4a63de6d1a293288058c8ac1d113255d0504e63f301",
                calculateSeedHex("あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あおぞら",
                        "",
                        Japanese.INSTANCE))
    }

    @Test
    fun normalize_Japanese_regular_spaces() {
        assertEquals("646f1a38134c556e948e6daef213609a62915ef568edb07ffa6046c87638b4b140fef2e0c6d7233af640c4a63de6d1a293288058c8ac1d113255d0504e63f301",
                calculateSeedHex("あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あおぞら",
                        "",
                        Japanese.INSTANCE))
    }

    @Test
    fun all_japanese_test_vectors() {
        val data = TestVectorJson.loadJapanese()
        for (testVector in data.vectors) {
            testSeedGeneration(testVector, Japanese.INSTANCE)
        }
    }

    @Test
    fun all_french_test_vectors() {
        val data = TestVectorJson.loadFrench()
        for (testVector in data.vectors) {
            testSeedGeneration(testVector, French.INSTANCE)
        }
    }

    @Test
    fun all_spanish_test_vectors() {
        val data = TestVectorJson.loadSpanish()
        for (testVector in data.vectors) {
            testSeedGeneration(testVector, Spanish.INSTANCE)
        }
    }

    private fun testSeedGeneration(testVector: TestVector, wordList: WordList) {
        assertEquals(testVector.seed, calculateSeedHex(testVector.mnemonic, testVector.passphrase, wordList))
    }

    private enum class ValidateMode {
        NOT_EXPECTING_BAD_WORD,
        EXPECTING_BAD_WORD
    }

    private fun calculateSeedHex(mnemonic: String, passphrase: String, validateMode: ValidateMode = ValidateMode.NOT_EXPECTING_BAD_WORD): String {
        return calculateSeedHex(mnemonic, passphrase, English.INSTANCE, validateMode)
    }

    private fun calculateSeedHex(mnemonic: String, passphrase: String, wordList: WordList, validateMode: ValidateMode = ValidateMode.NOT_EXPECTING_BAD_WORD): String {
        val mnemonic1 = Arrays.asList(*mnemonic.split("[ \u3000]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        return calculateSeedHex(mnemonic1, passphrase, wordList, validateMode)
    }

    private fun calculateSeedHex(mnemonic: Collection<CharSequence>, passphrase: String, wordList: WordList, validateMode: ValidateMode): String {
        val newMnemonic = mnemonic.stream()
                .map<CharSequence> { sequence ->
                    if (validateMode == ValidateMode.EXPECTING_BAD_WORD)
                        sequence
                    else
                        preventToStringAndSubSequence(sequence)
                }
                .collect(Collectors.toList())

        val seed1 = calculateSeed(newMnemonic, passphrase, SeedCalculator()
                .withWordsFromWordList(wordList))
        val seedCalculatorWithWords = SeedCalculator(JavaxPBKDF2WithHmacSHA512.INSTANCE)
                .withWordsFromWordList(wordList)
        val seed2 = calculateSeed(newMnemonic, passphrase, seedCalculatorWithWords)
        val seed3ForReuse = calculateSeed(newMnemonic, passphrase, seedCalculatorWithWords)
        assertEquals(seed1, seed2)
        assertEquals(seed1, seed3ForReuse)
        return seed1
    }


    private fun calculateSeed(mnemonic: Collection<CharSequence>, passphrase: String, seedCalculator: SeedCalculatorByWordListLookUp): String {
        return toHex(seedCalculator.calculateSeed(mnemonic, passphrase))
    }
}