package com.dcen.wallet.dellet.core.bip44

import com.dcen.wallet.dellet.core.bip32.Path

/**
 * Represents the first part of a BIP44 path. To create, use the static factory method [BIP44.m].
 * m / purpose' / coin_type' / account' / change / address_index
 */
class M
internal constructor(
        override
        val parent: Path? = null,
        override
        val value: Int = 0,
        override
        val level: Int = BIP44.ROOT_LEVEL) : Path {

    private val PURPOSE_44 = Purpose(this, 44)
    private val PURPOSE_49 = Purpose(this, 49)

    /**
     * Create a [Purpose].
     * For 44 and 49 this function is guaranteed to return the same instance.
     *
     * @param purpose The purpose number.
     * @return A purpose object.
     */
    fun purpose(purpose: Int): Purpose {
        return when (purpose) {
            44 -> PURPOSE_44
            49 -> PURPOSE_49
            else -> Purpose(this, purpose)
        }
    }

    fun purpose44(): Purpose = PURPOSE_44

    fun purpose49(): Purpose = PURPOSE_49

    override
    fun toString(): String = "m"
}