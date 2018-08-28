package com.dcen.wallet.dellet.core.bip32

import com.dcen.wallet.dellet.core.bip32.crypto.Crypto

interface ExtendedKey {

    /**
     * Extended key
     *
     * @return Extended key
     */
    val key: Key

    /**
     * Chain code
     *
     * @return Chain code
     */
    val chainCode: ByteArray

    /**
     * The crypto
     *
     * @return The crypto
     */
    var crypto: Crypto?

    /**
     * 1 byte: 0 for master nodes, 1 for level-1 derived keys, etc.
     *
     * @return the level of this key node
     */
    val level: Int

    /**
     * 4 bytes: child number. e.g. 3 for m/3, hard(7) for m/7'
     * 0 if master key
     *
     * @return the child number
     */
    val childNumber: Int

    /**
     * Serialized Base58 String of this extended key
     *
     * @return the Base58 String representing this key
     */
    val extendedBase58: String

    /**
     * Serialized data of this extended key
     *
     * @return the byte array representing this key
     */
    val extendedKeyByteArray: ByteArray

    /**
     * Coerce this key on to another crypto.
     *
     * @param otherCrypto Crypto to put key on.
     * @return A new extended key, or this instance if key already on the other Crypto.
     */
    fun toCrypto(otherCrypto: Crypto): ExtendedKey
}