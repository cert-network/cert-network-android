package com.dcen.wallet.dellet.core.bip32

import com.dcen.wallet.dellet.core.bip32.Checksum.checksum
import com.dcen.wallet.dellet.core.bip32.crypto.Crypto
import com.dcen.wallet.dellet.core.bip32.network.DefaultNetwork

internal class Serializer
private constructor(val crypto: Crypto?,
                    val neutered: Boolean,
                    val depth: Int,
                    val childNumber: Int,
                    val fingerprint: Int) {


    private val version: Int
        get() =
            if (neutered)
                crypto?.publicVersion ?: DefaultNetwork.MAIN_NET.getPublicVersion()
            else
                crypto?.privateVersion ?: DefaultNetwork.MAIN_NET.getPrivateVersion()


    fun serialize(key: ByteArray?, chainCode: ByteArray?): ByteArray {
        if (key == null)
            throw IllegalArgumentException("Key is null")
        if (chainCode == null)
            throw IllegalArgumentException("Chain code is null")
        if (chainCode.size != 32)
            throw IllegalArgumentException("Chain code must be 32 bytes")
        if (neutered) {
            if (key.size != 33)
                throw IllegalArgumentException("Key must be 33 bytes for neutered serialization")
        } else {
            if (key.size != 32)
                throw IllegalArgumentException("Key must be 32 bytes for non neutered serialization")
        }

        val privateKey = ByteArray(82)
        val writer = ByteArrayWriter(privateKey)
        writer.concatSer32(version)
        writer.concat(depth.toByte())
        writer.concatSer32(fingerprint)
        writer.concatSer32(childNumber)
        writer.concat(chainCode)
        if (!neutered) {
            writer.concat(0.toByte())
            writer.concat(key)
        } else {
            writer.concat(key)
        }
        writer.concat(checksum(privateKey), 4)
        return privateKey
    }

    internal class Builder {
        private var crypto: Crypto? = null
        private var neutered: Boolean = false
        private var depth: Int = 0
        private var childNumber: Int = 0
        private var fingerprint: Int = 0

        fun crypto(crypto: Crypto?): Builder = apply { this.crypto = crypto }

        fun neutered(neutered: Boolean): Builder = apply { this.neutered = neutered }

        fun depth(depth: Int): Builder = apply {
            if (depth < 0 || depth > 255)
                throw IllegalArgumentException("Depth must be [0..255]")
            this.depth = depth
        }

        fun childNumber(childNumber: Int): Builder = apply { this.childNumber = childNumber }

        fun fingerprint(fingerprint: Int): Builder = apply { this.fingerprint = fingerprint }

        fun build(): Serializer {
            return Serializer(
                    crypto = crypto,
                    neutered = neutered,
                    depth = depth,
                    childNumber = childNumber,
                    fingerprint = fingerprint)
        }
    }
}