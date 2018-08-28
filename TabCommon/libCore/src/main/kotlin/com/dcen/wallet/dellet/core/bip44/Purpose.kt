package com.dcen.wallet.dellet.core.bip44

import com.dcen.wallet.dellet.core.bip32.Path
import com.dcen.wallet.dellet.core.util.Index

/**
 * Represents the 1st level of a BIP44 path. To create, start with the static factory method [BIP44.m].
 * m / purpose' / coin_type' / account' / change / address_index
 *
 *
 * Purpose is a constant set to 44' (or 0x8000002C) following the BIP43 recommendation. It indicates that the subtree of
 * this node is used according to this specification.
 *
 *
 * Purpose 49 is used for segwit as per BIP0049.
 *
 *
 * Purpose 0 is reserved by BIP0032.
 *
 *
 * Hardened derivation is used at this level.
 */
class Purpose
internal constructor(
        override
        val parent: Path,
        override
        val value: Int,
        override
        val level: Int = BIP44.PURPOSE_LEVEL): Path {
    private val toString: String

    init {
        if (value == 0 || Index.isHardened(value))
            throw IllegalArgumentException()
        toString = String.format("%s/%d'", parent, value)
    }


    /**
     * Create a [CoinType] for this purpose.
     *
     * @param coinType The coin type
     * @return A coin type instance for this purpose
     */
    fun coinType(coinType: Int): CoinType {
        return CoinType(this, coinType)
    }

    override
    fun toString(): String = toString

}