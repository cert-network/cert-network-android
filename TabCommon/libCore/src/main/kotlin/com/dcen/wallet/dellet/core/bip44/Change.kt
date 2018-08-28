package com.dcen.wallet.dellet.core.bip44

import com.dcen.wallet.dellet.core.bip32.Path

/**
 * The 5th part of a BIP44 path, create via a [Account].
 * m / purpose' / coin_type' / account' / change / address_index
 *
 *
 * Constant 0 is used for external chain and constant 1 for internal chain (also known as change addresses).
 * External chain is used for addresses that are meant to be visible outside of the wallet (e.g. for receiving
 * payments).
 * Internal chain is used for addresses which are not meant to be visible outside of the wallet and is used for return
 * transaction change.
 *
 *
 * Public derivation is used at this level (not hardened).
 */
class Change
internal constructor(
        override
        val parent: Path,
        override
        val value: Int,
        override
        val level: Int = BIP44.CHANGE_LEVEL
): Path {

    private val string: String = String.format("%s/%d", parent, value)


    /**
     * Create a [AddressIndex] for this purpose, coin type, account and change.
     *
     * @param addressIndex The index of the child
     * @return A coin type instance for this purpose, coin type, account and change.
     */
    fun address(addressIndex: Int): AddressIndex = AddressIndex(this, addressIndex)

    override
    fun toString(): String = string
}