package com.dcen.wallet.dellet.core.util.base58

interface SecureEncoder {

    /**
     * Encodes given bytes as a number in base58.
     *
     * @param bytes  bytes to encode
     * @param target where to write resulting string to
     */
    fun encode(bytes: ByteArray, target: EncodeTarget)


    /**
     * Encodes given bytes as a number in base58.
     *
     * @param bytes       bytes to encode
     * @param setCapacity a callback to the target to set its capacity
     * @param target      where to write resulting string to
     */
    fun encode(bytes: ByteArray, setCapacity: EncodeTargetCapacity, target: EncodeTarget)

    /**
     * Encodes given bytes as a number in base58.
     *
     * @param bytes  bytes to encode
     * @param target where to write resulting string to
     */
    fun encode(bytes: ByteArray, target: EncodeTargetFromCapacity)
}