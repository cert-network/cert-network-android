package com.dcen.wallet.dellet.core.bip32.derivation

import com.dcen.wallet.dellet.core.bip32.ExtendedKey

enum class IntArrayDerivation : Derivation<IntArray> {
    INSTANCE;

    override
    fun <KEY: ExtendedKey> derive(rootKey: KEY, path: IntArray?, ckdFunction: CkdFunction<KEY>): KEY {
        var currentKey = rootKey
        path?.forEachIndexed { index, p ->
            currentKey = ckdFunction.deriveChildKey(currentKey, p, index + 1)
        }
        return currentKey
    }
}