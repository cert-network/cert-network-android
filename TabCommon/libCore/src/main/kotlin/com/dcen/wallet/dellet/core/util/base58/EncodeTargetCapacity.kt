package com.dcen.wallet.dellet.core.util.base58

interface EncodeTargetCapacity {

    /**
     * Sets capacity required for encoding. This may be over the actual required size, but never under.
     *
     * @param characters Maximum number of base58 characters required during encoding.
     */
    fun setCapacity(characters: Int)
}