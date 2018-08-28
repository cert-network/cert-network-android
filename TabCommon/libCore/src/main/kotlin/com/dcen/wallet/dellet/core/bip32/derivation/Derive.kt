package com.dcen.wallet.dellet.core.bip32.derivation

import com.dcen.wallet.dellet.core.bip32.Path

interface Derive<KEY> {

    /**
     * Derive from a string path such as [44,0,0,0,1]
     *
     * @param derivationPath Path
     * @return Key at the path
     */
    fun derive(derivationPath: IntArray): KEY

    /**
     * Derive from a string path such as m/44'/0'/0'/0/1
     *
     * @param derivationPath Path
     * @return Key at the path
     */
    fun derive(derivationPath: CharSequence): KEY

    /**
     * Derive from a generic path using the [Derivation] supplied to extract the child indexes
     *
     * @param derivationPath Path
     * @param derivation     The class that extracts the path elements
     * @param <Path>         The generic type of the path
     * @return Key at the path
     */
    fun derive(derivationPath: Path): KEY
}