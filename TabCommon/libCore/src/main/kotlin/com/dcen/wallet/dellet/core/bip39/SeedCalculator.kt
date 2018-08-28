package com.dcen.wallet.dellet.core.bip39

import com.dcen.wallet.dellet.core.bip39.Normalization.normalizeNFKD
import com.dcen.wallet.dellet.core.util.Func
import com.dcen.wallet.dellet.core.util.toRuntime
import java.util.*


/**
 * Contains function for generating seeds from a Mnemonic and Passphrase.
 *
 * Creates a seed calculator using {@link SpongyCastlePBKDF2WithHmacSHA512} which is the most compatible.
 * Use {@link SeedCalculator#SeedCalculator(PBKDF2WithHmacSHA512)} to supply another.
 */
class SeedCalculator
@JvmOverloads
constructor(
        private val hashAlgorithm: PBKDF2WithHmacSHA512 = SpongyCastlePBKDF2WithHmacSHA512.INSTANCE
) {

    private val fixedSalt = getUtf8Bytes("mnemonic")

    /**
     * Calculate the seed given a mnemonic and corresponding passphrase.
     * The phrase is not checked for validity here, for that use a [MnemonicValidator].
     *
     *
     * Due to normalization, these need to be [String], and not [CharSequence], this is an open issue:
     * https://github.com/NovaCrypto/BIP39/issues/7
     *
     *
     * If you have a list of words selected from a word list, you can use [.withWordsFromWordList] then
     * [SeedCalculatorByWordListLookUp.calculateSeed]
     *
     * @param mnemonic   The memorable list of words
     * @param passphrase An optional passphrase, use "" if not required
     * @return a seed for HD wallet generation
     */
    fun calculateSeed(mnemonic: String, passphrase: String): ByteArray {
        val chars = normalizeNFKD(mnemonic).toCharArray()
        try {
            return calculateSeed(chars, passphrase)
        } finally {
            Arrays.fill(chars, '\u0000')
        }
    }

    fun calculateSeed(mnemonicChars: CharArray, passphrase: String): ByteArray {
        val normalizedPassphrase = normalizeNFKD(passphrase)
        val salt2 = getUtf8Bytes(normalizedPassphrase)
        val salt = combine(fixedSalt, salt2)
        clear(salt2)
        val encoded = hash(mnemonicChars, salt)
        clear(salt)
        return encoded
    }


    fun withWordsFromWordList(wordList: WordList): SeedCalculatorByWordListLookUp {
        return SeedCalculatorByWordListLookUp(this, wordList)
    }


    private fun combine(array1: ByteArray, array2: ByteArray): ByteArray {
        val bytes = ByteArray(array1.size + array2.size)
        System.arraycopy(array1, 0, bytes, 0, array1.size)
        System.arraycopy(array2, 0, bytes, array1.size, bytes.size - array1.size)
        return bytes
    }

    private fun clear(salt: ByteArray) {
        Arrays.fill(salt, 0.toByte())
    }

    private fun hash(chars: CharArray, salt: ByteArray): ByteArray {
        return hashAlgorithm.hash(chars, salt)
    }

    private fun getUtf8Bytes(string: String): ByteArray {
        return toRuntime(object : Func<ByteArray> {
            @Throws(Exception::class)
            override fun run(): ByteArray {
                return string.toByteArray(charset("UTF-8"))
            }
        })
    }
}
