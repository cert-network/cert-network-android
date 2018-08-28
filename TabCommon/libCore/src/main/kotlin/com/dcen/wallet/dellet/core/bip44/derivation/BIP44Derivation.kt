package com.dcen.wallet.dellet.core.bip44.derivation


import com.dcen.wallet.dellet.core.bip32.ExtendedKey
import com.dcen.wallet.dellet.core.bip32.Path
import com.dcen.wallet.dellet.core.bip32.derivation.CkdFunction
import com.dcen.wallet.dellet.core.bip32.derivation.Derivation
import com.dcen.wallet.dellet.core.bip44.BIP44
import com.dcen.wallet.dellet.core.bip44.constant.MapCoinType
import com.dcen.wallet.dellet.core.bip44.constant.BIP44CoinType.NO_CRYPTO
import com.dcen.wallet.dellet.core.util.Index

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class BIP44Derivation(
        private val endLevel: Int
) : Derivation<Path> {

    override
    fun <KEY : ExtendedKey> derive(rootKey: KEY, path: Path?, ckdFunction: CkdFunction<KEY>): KEY {
        val levelPath: Int = path?.level ?: 0
        var pathValue: Int = path?.value ?: 0

        if (levelPath == BIP44.PURPOSE_LEVEL
                || levelPath == BIP44.COIN_TYPE_LEVEL
                || levelPath == BIP44.ACCOUNT_LEVEL) {
            pathValue = Index.hard(pathValue)
        }

        if (levelPath == BIP44.COIN_TYPE_LEVEL) {
            rootKey.crypto = MapCoinType.getCrypto(path?.value ?: NO_CRYPTO)
        }

        if (levelPath <= endLevel + 1) {
            return ckdFunction.deriveChildKey(
                    rootKey,
                    pathValue,
                    levelPath)
        }

        return ckdFunction.deriveChildKey(
                derive(rootKey, path?.parent, ckdFunction),
                pathValue,
                levelPath)
    }

}