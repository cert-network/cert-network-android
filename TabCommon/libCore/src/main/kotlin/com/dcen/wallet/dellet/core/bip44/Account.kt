package com.dcen.wallet.dellet.core.bip44


import com.dcen.wallet.dellet.core.bip32.Path
import com.dcen.wallet.dellet.core.util.Index

/**
 * The 4th level of a BIP44 path, create via a [Change].
 * m / purpose' / coin_type' / account' / change / address_index
 *
 *
 * This level splits the key space into independent user identities, so the wallet never mixes the coins across
 * different accounts.
 *
 *
 * Users can use these accounts to organize the funds in the same fashion as bank accounts; for donation purposes
 * (where all addresses are considered public), for saving purposes, for common expenses etc.
 *
 *
 * Accounts are numbered from index 0 in sequentially increasing manner. This number is used as child index in BIP32
 * derivation.
 *
 *
 * Hardened derivation is used at this level.
 */
class Account
internal constructor(
        override
        val parent: Path,
        override
        val value: Int,
        override
        val level: Int = BIP44.ACCOUNT_LEVEL
) : Path {

    init {
        if (Index.isHardened(value))
            throw IllegalArgumentException()
    }


    /**
     * Create a [Change] for this purpose, coin type and account.
     *
     *
     * Constant 0 is used for external chain.
     * External chain is used for addresses that are meant to be visible outside of the wallet (e.g. for receiving
     * payments).
     *
     * @return A [Change] = 0 instance for this purpose, coin type and account
     */
    fun external(): Change {
        return Change(this, 0)
    }

    /**
     * Create a [Change] for this purpose, coin type and account.
     *
     *
     * Constant 1 is used for internal chain (also known as change addresses).
     * Internal chain is used for addresses which are not meant to be visible outside of the wallet and is used for
     * return transaction change.
     *
     * @return A [Change] = 1 instance for this purpose, coin type and account
     */
    fun internal(): Change {
        return Change(this, 1)
    }

    override
    fun toString(): String = String.format("%s/%d'", parent, value)


}