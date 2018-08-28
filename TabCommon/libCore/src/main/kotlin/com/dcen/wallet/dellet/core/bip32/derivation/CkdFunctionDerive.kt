package com.dcen.wallet.dellet.core.bip32.derivation

import com.dcen.wallet.dellet.core.bip32.ExtendedKey
import com.dcen.wallet.dellet.core.bip32.Path
import com.dcen.wallet.dellet.core.bip44.derivation.BIP44Derivation

class CkdFunctionDerive<KEY : ExtendedKey>(
        private val pathLevel: Int,
        private val standardCkdFunction: CkdFunction<KEY>,
        private val rootNode: KEY) : Derive<KEY> {

    override
    fun derive(derivationPath: IntArray): KEY {
        return IntArrayDerivation.INSTANCE.derive(rootNode, derivationPath, standardCkdFunction)
    }

    override
    fun derive(derivationPath: CharSequence): KEY {
        return CharSequenceDerivation.INSTANCE.derive(rootNode, derivationPath, standardCkdFunction)
    }

    override
    fun derive(derivationPath: Path): KEY {
        return BIP44Derivation(pathLevel).derive(rootNode, derivationPath, standardCkdFunction)
    }


}