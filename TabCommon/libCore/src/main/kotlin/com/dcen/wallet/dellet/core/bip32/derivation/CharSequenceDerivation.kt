package com.dcen.wallet.dellet.core.bip32.derivation


import com.dcen.wallet.dellet.core.bip32.ExtendedKey
import com.dcen.wallet.dellet.core.bip44.BIP44
import com.dcen.wallet.dellet.core.bip44.constant.MapCoinType
import com.dcen.wallet.dellet.core.util.Index

enum class CharSequenceDerivation : Derivation<CharSequence> {
    INSTANCE;

    override
    fun <KEY : ExtendedKey> derive(rootKey: KEY, path: CharSequence?, ckdFunction: CkdFunction<KEY>): KEY {
        val length = path?.length ?: 0
        if (length == 0)
            throw IllegalArgumentException("Path cannot be empty")
        if (path?.get(0) != 'm')
            throw IllegalArgumentException("Path must start with m")
        if (length == 1)
            return rootKey
        if (path[1] != '/')
            throw IllegalArgumentException("Path must start with m/")
        var currentKey = rootKey
        var buffer = 0
        var level = 1
        for (i in 2 until length) {
            val c: Char = path[i]
            when (c) {
                '\'' ->{
                    if (level == BIP44.COIN_TYPE_LEVEL) {
                        currentKey.crypto = MapCoinType.getCrypto(buffer)
                    }
                    buffer = Index.hard(buffer)
                }
                '/' -> {
                    currentKey = ckdFunction.deriveChildKey(currentKey, buffer, level)
                    buffer = 0
                    level += 1
                }
                else -> {
                    buffer *= 10
                    if (c < '0' || c > '9')
                        throw IllegalArgumentException("Illegal character in path: $c")
                    buffer += c - '0'
                    if (Index.isHardened(buffer))
                        throw IllegalArgumentException("Index number too large")
                }
            }
        }
        return ckdFunction.deriveChildKey(currentKey, buffer, 0)
    }
}