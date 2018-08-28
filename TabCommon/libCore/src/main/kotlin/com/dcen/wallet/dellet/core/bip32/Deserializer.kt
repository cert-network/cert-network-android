package com.dcen.wallet.dellet.core.bip32

interface Deserializer<T> {

    /**
     * Deserializes the data into a [T].
     *
     * @param extendedBase58Key Base58 CharSequence containing the serialized extended key.
     * @return The [T]
     */
    fun deserialize(extendedBase58Key: CharSequence): T

    /**
     * Deserializes the data into a [T].
     *
     * @param extendedKeyData Byte array containing the serialized extended key.
     * @return The [T]
     */
    fun deserialize(extendedKeyData: ByteArray): T
}