package com.dcen.wallet.dellet.core.bip39

import com.dcen.wallet.dellet.core.bip39.testjson.EnglishJson
import com.dcen.wallet.dellet.core.bip39.testjson.TestVectorJson
import com.dcen.wallet.dellet.core.bip39.wordlists.English
import com.dcen.wallet.dellet.core.bip39.wordlists.French
import com.dcen.wallet.dellet.core.bip39.wordlists.Japanese
import com.dcen.wallet.dellet.core.bip39.wordlists.Spanish
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Assert.assertEquals
import org.junit.Test

class MnemonicGenerationTests {

    private fun createMnemonic(f: String, wordList: WordList): String {
        return MnemonicGenerator
                .createMnemonic(wordList, f)
                .toString()
    }

    private fun createMnemonic(f: ByteArray, wordList: WordList): String {
        return MnemonicGenerator
                .createMnemonic(wordList,f).toString()
    }

    @Test
    @Throws(Exception::class)
    fun tooSmallEntropy() {
        assertThatThrownBy { createMnemonic(repeatString(30, "f"), English.INSTANCE) }
                .isInstanceOf(RuntimeException::class.java)
                .hasMessage("Entropy too low, 128-256 bits allowed")
    }

    @Test
    @Throws(Exception::class)
    fun tooSmallEntropyBytes() {
        assertThatThrownBy { createMnemonic(ByteArray(15), English.INSTANCE) }
                .isInstanceOf(RuntimeException::class.java)
                .hasMessage("Entropy too low, 128-256 bits allowed")
    }

    @Test
    @Throws(Exception::class)
    fun tooLargeEntropy() {
        assertThatThrownBy { createMnemonic(repeatString(66, "f"), English.INSTANCE) }
                .isInstanceOf(RuntimeException::class.java)
                .hasMessage("Entropy too high, 128-256 bits allowed")
    }

    @Test
    @Throws(Exception::class)
    fun tooLargeEntropyBytes() {
        assertThatThrownBy { createMnemonic(ByteArray(33), English.INSTANCE) }
                .isInstanceOf(RuntimeException::class.java)
                .hasMessage("Entropy too high, 128-256 bits allowed")
    }

    @Test
    @Throws(Exception::class)
    fun nonMultipleOf32() {
        assertThatThrownBy { createMnemonic(repeatString(34, "f"), English.INSTANCE) }
                .isInstanceOf(RuntimeException::class.java)
                .hasMessage("Number of entropy bits must be divisible by 32")
    }

    @Test
    @Throws(Exception::class)
    fun notHexPairs() {
        assertThatThrownBy { createMnemonic(repeatString(33, "f"), English.INSTANCE) }
                .isInstanceOf(RuntimeException::class.java)
                .hasMessage("Length of hex chars must be divisible by 2")
    }

    @Test
    @Throws(Exception::class)
    fun sevenFRepeated() {
        assertEquals("legal winner thank year wave sausage worth useful legal winner thank year wave sausage worth useful legal will",
                createMnemonic("7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f", English.INSTANCE)
        )
    }

    @Test
    @Throws(Exception::class)
    fun eightZeroRepeated() {
        assertEquals("letter advice cage absurd amount doctor acoustic avoid letter advice cage above",
                createMnemonic("80808080808080808080808080808080", English.INSTANCE)
        )
    }

    @Test
    @Throws(Exception::class)
    fun all_english_test_vectors() {
        val data = EnglishJson.load()
        for (testCase in data.english) {
            assertEquals(testCase[1], createMnemonic(testCase[0], English.INSTANCE))
        }
    }

    @Test
    @Throws(Exception::class)
    fun all_japanese_test_vectors() {
        val data = TestVectorJson.loadJapanese()
        for (testVector in data.vectors) {
            assertEquals(testVector.mnemonic, createMnemonic(testVector.entropy, Japanese.INSTANCE))
        }
    }

    @Test
    @Throws(Exception::class)
    fun all_french_test_vectors() {
        val data = TestVectorJson.loadFrench()
        for (testVector in data.vectors) {
            assertEquals(testVector.mnemonic, createMnemonic(testVector.entropy, French.INSTANCE))
        }
    }

    @Test
    @Throws(Exception::class)
    fun all_spanish_test_vectors() {
        val data = TestVectorJson.loadSpanish()
        for (testVector in data.vectors) {
            assertEquals(testVector.mnemonic, createMnemonic(testVector.entropy, Spanish.INSTANCE))
        }
    }

    @Test
    @Throws(Exception::class)
    fun upper_and_lower_case_hex_handled_the_same() {
        val hex = "0123456789abcdef0123456789abcdef"
        assertEquals(createMnemonic(hex, English.INSTANCE),
                createMnemonic(hex.toUpperCase(), English.INSTANCE))
    }

    @Test
    @Throws(Exception::class)
    fun bad_hex_throws_g() {
        val hex = "0123456789abcdef0123456789abcdeg"
        assertThatThrownBy { createMnemonic(hex, English.INSTANCE) }
                .isInstanceOf(RuntimeException::class.java)
                .hasMessage("Invalid hex char 'g'")
    }

    @Test
    @Throws(Exception::class)
    fun bad_hex_throws_Z() {
        val hex = "0123456789abcdef0123456789abcdeZ"
        assertThatThrownBy { createMnemonic(hex, English.INSTANCE) }
                .isInstanceOf(RuntimeException::class.java)
                .hasMessage("Invalid hex char 'Z'")
    }

    @Test
    @Throws(Exception::class)
    fun bad_hex_throws_space() {
        val hex = "0123456789 abcdef0123456789abcde"
        assertThatThrownBy { createMnemonic(hex, English.INSTANCE) }
                .isInstanceOf(RuntimeException::class.java)
                .hasMessage("Invalid hex char ' '")
    }


    private fun repeatString(n: Int, repeat: String): String {
        return String(CharArray(n)).replace("\u0000", repeat)
    }
}
