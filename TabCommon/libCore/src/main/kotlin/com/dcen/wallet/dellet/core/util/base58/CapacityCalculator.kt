package com.dcen.wallet.dellet.core.util.base58

internal object CapacityCalculator {

    private val log2_58 = Math.log(58.0) / Math.log(2.0)

    private val storageRatio = 8.0 / log2_58

    /**
     * Calculates the maximum length of a base58 string using formula:
     *
     *
     * maxLength characters = length bytes * 8 bits per byte / Log2(58) bits per character
     *
     *
     * The length may be less depending on the actual data inside the array.
     *
     *
     *
     * @return equivalent to `base58Encode(new byte[byteLength]{0xff,0xff,0xff...}).length`
     */
    @JvmStatic
    fun maximumBase58StringLength(byteLength: Int): Int {
        return Math.ceil(byteLength * storageRatio).toInt()
    }
}