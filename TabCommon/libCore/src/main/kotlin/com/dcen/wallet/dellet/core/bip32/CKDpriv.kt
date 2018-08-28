package com.dcen.wallet.dellet.core.bip32

interface CKDpriv {

    /**
     * Calculates the private key of the child at index.
     *
     * @param index The child index to calculate.
     * @param pathLevel
     * @return The private key of the child.
     */
    fun cKDpriv(index: Int, pathLevel: Int): ExtendedPrivateKey
}
