package com.dcen.wallet.dellet.core.bip44


import com.dcen.wallet.dellet.core.bip32.Path
import com.dcen.wallet.dellet.core.util.Index

/**
 * Represents the 3rd part of a BIP44 path. Create via a [Purpose].
 * m / purpose' / coin_type' / account' / change / address_index
 */
class CoinType
internal constructor(
        override
        val parent: Path,
        override
        val value: Int,
        override
        val level: Int = BIP44.COIN_TYPE_LEVEL) : Path {


    private val string: String

    init {
        if (Index.isHardened(value))
            throw IllegalArgumentException()
        string = String.format("%s/%d'", parent, value)
    }


    /**
     * Create a [Account] for this purpose and coin type.
     *
     * @param account The account number
     * @return An [Account] instance for this purpose and coin type
     */
    fun account(account: Int): Account {
        return Account(this, account)
    }

    override
    fun toString(): String = string
}


