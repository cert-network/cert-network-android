package com.dcen.wallet.dellet.core.bip32.network

/**
 * Network represents the crypto to use.
 */
interface Network {
    fun getPrivateVersion(): Int

    fun getPublicVersion(): Int

    fun p2pkhVersion(): Byte

    fun p2shVersion(): Byte
}
