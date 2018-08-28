package com.dcen.wallet.dellet.core.bip39

import com.dcen.wallet.dellet.core.bip32.ExtendedPrivateKey
import util.Hex.toHex
import com.dcen.wallet.dellet.core.bip39.testjson.EnglishJson
import com.dcen.wallet.dellet.core.bip39.testjson.TestVector
import com.dcen.wallet.dellet.core.bip39.testjson.TestVectorJson
import org.junit.Assert.assertEquals
import org.junit.Test

class SeedCalculationTests {

    @Test
    fun bip39_english() {
        assertEquals("2eea1e4d099089606b7678809be6090ccba0fca171d4ed42c550194ca8e3600cd1e5989dcca38e5f903f5c358c92e0dcaffc9e71a48ad489bb868025c907d1e1",
                calculateSeedHex("solar puppy hawk oxygen trip brief erase slot fossil mechanic filter voice"))
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
                calculateSeedHex("あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あおぞら"))
    }

    @Test
    fun normalize_Japanese_2() {
        assertEquals("646f1a38134c556e948e6daef213609a62915ef568edb07ffa6046c87638b4b140fef2e0c6d7233af640c4a63de6d1a293288058c8ac1d113255d0504e63f301",
                calculateSeedHex("あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あいこくしん　あおぞら"))
    }

    @Test
    fun normalize_Japanese_regular_spaces() {
        assertEquals("646f1a38134c556e948e6daef213609a62915ef568edb07ffa6046c87638b4b140fef2e0c6d7233af640c4a63de6d1a293288058c8ac1d113255d0504e63f301",
                calculateSeedHex("あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あいこくしん あおぞら"))
    }

    @Test
    fun all_japanese_test_vectors() {
        val data = TestVectorJson.loadJapanese()
        for (testVector in data.vectors) {
            testSeedGeneration(testVector)
        }
    }

    @Test
    fun all_french_test_vectors() {
        val data = TestVectorJson.loadFrench()
        for (testVector in data.vectors) {
            testSeedGeneration(testVector)
        }
    }

    @Test
    fun all_spanish_test_vectors() {
        val data = TestVectorJson.loadSpanish()
        for (testVector in data.vectors) {
            testSeedGeneration(testVector)
        }
    }

    private fun testSeedGeneration(testVector: TestVector) {
        val seed = SeedCalculator().calculateSeed(testVector.mnemonic, testVector.passphrase)
        assertEquals(testVector.seed, toHex(seed))
        assertEquals(testVector.bip32Xprv, ExtendedPrivateKey.fromSeed(seed).extendedBase58)
    }

    private fun calculateSeedHex(mnemonic: String, passphrase: String = ""): String {
        val seed1 = calculateSeed(mnemonic, passphrase, SeedCalculator())
        val seed2 = calculateSeed(mnemonic, passphrase, SeedCalculator(JavaxPBKDF2WithHmacSHA512.INSTANCE))
        assertEquals(seed1, seed2)
        return seed1
    }

    private fun calculateSeed(mnemonic: String, passphrase: String, seedCalculator: SeedCalculator): String {
        return toHex(seedCalculator.calculateSeed(mnemonic, passphrase))
    }
}