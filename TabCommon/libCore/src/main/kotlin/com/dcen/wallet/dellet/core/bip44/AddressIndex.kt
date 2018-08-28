package com.dcen.wallet.dellet.core.bip44


import com.dcen.wallet.dellet.core.bip32.Path
import com.dcen.wallet.dellet.core.util.Index

/**
 * The 6th and final part of a BIP44 path, create via a [Change].
 * m / purpose' / coin_type' / account' / change / address_index
 *
 *
 * Addresses are numbered from index 0 in sequentially increasing manner. This number is used as child index in BIP32
 * derivation.
 *
 *
 * Public derivation is used at this level (not hardened).
 */
class AddressIndex
internal constructor(
        override
        val parent: Path,
        override
        val value: Int,
        override
        val level: Int = BIP44.ADDRESS_LEVEL
) : Path {


    init {
        if (Index.isHardened(value))
            throw IllegalArgumentException()
    }

    override
    fun toString(): String = String.format("%s/%d", parent, value)

}