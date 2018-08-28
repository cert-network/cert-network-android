package com.dcen.wallet.dellet.core.bip39

import java.text.Normalizer

internal object Normalization {

    @JvmStatic
    fun normalizeNFKD(string: String): String {
        return Normalizer.normalize(string, Normalizer.Form.NFKD)
    }

    @JvmStatic
    fun normalizeNFKD(c: Char): Char {
        return normalizeNFKD("" + c)[0]
    }
}