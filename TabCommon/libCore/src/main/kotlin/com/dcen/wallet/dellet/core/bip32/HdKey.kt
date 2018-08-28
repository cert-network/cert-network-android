package com.dcen.wallet.dellet.core.bip32

import com.dcen.wallet.dellet.core.bip32.crypto.Crypto
import com.dcen.wallet.dellet.core.util.and
import com.dcen.wallet.dellet.core.util.hash160
import com.dcen.wallet.dellet.core.util.parse256

class HdKey private constructor(
        val level: Int = START_LEVEL,
        private val neutered: Boolean,
        var crypto: Crypto?,
        val chainCode: ByteArray = ByteArray(0),
        val key: ByteArray = ByteArray(0),
        val keyUncompressed: ByteArray,
        private val serializer: Serializer,
        val parentFingerprint: Int,
        val childNumber: Int
) {

    companion object {
        const val START_LEVEL = 0
    }

    val point: ByteArray
        get() = Secp256k1SC.pointSerP_gMultiply(key.parse256, true)

    val pointUncompress: ByteArray
        get() = Secp256k1SC.pointSerP_gMultiply(key.parse256, false)

    private val publicBuffer: ByteArray
        get() = if (neutered) key else point


    fun serialize(): ByteArray {
        return serializer.serialize(key, chainCode)
    }

    fun calculateFingerPrint(): Int {
        val point = publicBuffer
        val o = point.hash160
        return (((o[0] and 0xFF) shl 24) or
                ((o[1] and 0xFF) shl 16) or
                ((o[2] and 0xFF) shl 8) or
                ((o[3] and 0xFF)))
    }


    internal fun toBuilder(): Builder {
        return Builder()
                .neutered(neutered)
                .chainCode(chainCode)
                .key(key)
                .level(level)
                .childNumber(childNumber)
                .parentFingerprint(parentFingerprint)
    }

    internal class Builder {

        private var crypto: Crypto? = null
        private var neutered: Boolean = false
        private var chainCode: ByteArray? = null
        private var key: ByteArray? = null
        private var keyUncompressed: ByteArray? = null
        private var level: Int = START_LEVEL
        private var childNumber: Int = 0
        private var parentFingerprint: Int = 0

        fun crypto(crypto: Crypto?): Builder = apply { this.crypto = crypto }

        fun neutered(neutered: Boolean): Builder = apply { this.neutered = neutered }

        fun key(key: ByteArray?): Builder = apply { this.key = key }

        fun keyUncompressed(key: ByteArray): Builder = apply { this.keyUncompressed = key }

        fun chainCode(chainCode: ByteArray?): Builder = apply { this.chainCode = chainCode }

        fun level(level: Int): Builder = apply { this.level = level }

        fun childNumber(childNumber: Int): Builder = apply { this.childNumber = childNumber }

        fun parentFingerprint(parentFingerprint: Int): Builder = apply { this.parentFingerprint = parentFingerprint }

        fun build(): HdKey {
            return HdKey(
                    neutered = neutered,
                    crypto = crypto,
                    key = key ?: ByteArray(0),
                    keyUncompressed = keyUncompressed ?: ByteArray(0),
                    parentFingerprint = parentFingerprint,
                    childNumber = childNumber,
                    chainCode = chainCode ?: ByteArray(0),
                    level = level,
                    serializer = Serializer.Builder()
                            .crypto(crypto)
                            .neutered(neutered)
                            .depth(level)
                            .childNumber(childNumber)
                            .fingerprint(parentFingerprint)
                            .build()

            )
        }
    }
}

