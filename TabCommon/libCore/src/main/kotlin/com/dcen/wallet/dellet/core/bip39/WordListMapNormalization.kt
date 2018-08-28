package com.dcen.wallet.dellet.core.bip39

import java.text.Normalizer
import java.util.*

internal class WordListMapNormalization(wordList: WordList) : NFKDNormalizer {

    private val normalizedMap = HashMap<CharSequence, String>()

    init {
        for (i in 0 until (1 shl 11)) {
            val word = wordList.getWord(i)
            val normalized = Normalizer.normalize(word, Normalizer.Form.NFKD)
            normalizedMap[word] = normalized
            normalizedMap[normalized] = normalized
            normalizedMap[Normalizer.normalize(word, Normalizer.Form.NFC)] = normalized
        }
    }

    override
    fun normalize(charSequence: CharSequence): String {
        val normalized = normalizedMap[charSequence]
        return normalized ?: Normalizer.normalize(charSequence, Normalizer.Form.NFKD)
    }
}