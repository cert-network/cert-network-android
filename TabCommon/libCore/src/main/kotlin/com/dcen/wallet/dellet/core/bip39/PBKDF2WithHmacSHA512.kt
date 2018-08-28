package com.dcen.wallet.dellet.core.bip39

interface PBKDF2WithHmacSHA512 {

    fun hash(chars: CharArray, salt: ByteArray): ByteArray
}
