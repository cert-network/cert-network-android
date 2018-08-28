package com.dcen.wallet.dellet.core.bip39

enum class Words
constructor(private val bitLength: Int) {
    TWELVE(128),
    FIFTEEN(160),
    EIGHTEEN(192),
    TWENTY_ONE(224),
    TWENTY_FOUR(256);

    fun bitLength(): Int {
        return bitLength
    }

    fun byteLength(): Int {
        return bitLength / 8
    }
}