package com.dcen.wallet.dellet.core.bip44

object BIP44 {

    const val MAX_LEVEL = 5

    const val ADDRESS_LEVEL = 5
    const val CHANGE_LEVEL = 4
    const val ACCOUNT_LEVEL = 3
    const val COIN_TYPE_LEVEL = 2
    const val PURPOSE_LEVEL = 1
    const val ROOT_LEVEL = 0

    private val M = M()

    /**
     * Start creating a BIP44 path.
     *
     * @return A fluent factory for creating BIP44 paths
     */
    fun m(): M = M


}