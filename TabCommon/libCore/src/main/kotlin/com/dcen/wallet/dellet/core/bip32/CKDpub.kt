package com.dcen.wallet.dellet.core.bip32

interface CKDpub {

    /**
     * Calculates the public key of the child at index.
     *
     * @param index The child index to calculate.
     * @param pathLevel
     * @return The public key of the child.
     */
    fun cKDpub(index: Int, pathLevel: Int): ExtendedPublicKey
}