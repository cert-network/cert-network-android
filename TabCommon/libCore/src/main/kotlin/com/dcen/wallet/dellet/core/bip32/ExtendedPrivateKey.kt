package com.dcen.wallet.dellet.core.bip32


import com.dcen.wallet.dellet.core.bip32.ByteArrayWriter.Companion.head32
import com.dcen.wallet.dellet.core.bip32.ByteArrayWriter.Companion.tail32
import com.dcen.wallet.dellet.core.bip32.crypto.Crypto
import com.dcen.wallet.dellet.core.bip32.derivation.CkdFunction
import com.dcen.wallet.dellet.core.bip32.derivation.CkdFunctionDerive
import com.dcen.wallet.dellet.core.bip32.derivation.CkdFunctionResultCacheDecorator.Companion.newCacheOf
import com.dcen.wallet.dellet.core.bip32.derivation.Derive
import com.dcen.wallet.dellet.core.util.*
import com.dcen.wallet.dellet.core.util.base58.Base58.base58Encode
import java.math.BigInteger
import java.util.*

/**
 * A BIP32 private key
 */
@Suppress("PrivatePropertyName")
class ExtendedPrivateKey
internal constructor(
        private val hdKey: HdKey
) : Derive<ExtendedPrivateKey>, CKDpriv, CKDpub, ExtendedKey {

    companion object {

        private val BITCOIN_SEED = getBytes("Bitcoin seed")

        fun fromSeed(seed: ByteArray): ExtendedPrivateKey {
            val I = BITCOIN_SEED.hmacSha512(seed)

            val Il = head32(I)
            val Ir = tail32(I)

            return ExtendedPrivateKey(Il, Ir)
        }

        private fun getBytes(seed: String): ByteArray {
            return toRuntime(object : Func<ByteArray> {
                @Throws(Exception::class)
                override fun run(): ByteArray {
                    return seed.toByteArray(charset("UTF-8"))
                }
            })
        }
    }

    override
    val key: PrivateKey
        get() = PrivateKey(hdKey)


    override
    val chainCode: ByteArray
        get() = hdKey.chainCode

    override
    val extendedKeyByteArray: ByteArray
        get() = hdKey.serialize()

    override
    val extendedBase58: String
        get() = base58Encode(extendedKeyByteArray)

    override
    var crypto: Crypto?
        get() = hdKey.crypto
        set(value) {
            hdKey.crypto = value
        }

    override
    val level: Int
        get() = hdKey.level

    override
    val childNumber: Int
        get() = hdKey.childNumber

    private val CKD_FUNCTION = object : CkdFunction<ExtendedPrivateKey> {
        override
        fun deriveChildKey(parent: ExtendedPrivateKey, childIndex: Int, level: Int): ExtendedPrivateKey {
            return parent.cKDpriv(childIndex, level)
        }
    }


    private constructor(key: ByteArray, chainCode: ByteArray) : this(
            HdKey.Builder()
                    .level(0)
                    .neutered(false)
                    .key(key)
                    .chainCode(chainCode)
                    .childNumber(0)
                    .parentFingerprint(0)
                    .build())


    override
    fun toCrypto(otherCrypto: Crypto): ExtendedPrivateKey {
        return if (otherCrypto == crypto) this else ExtendedPrivateKey(
                hdKey.toBuilder()
                        .crypto(otherCrypto)
                        .build())
    }


    override
    fun cKDpriv(index: Int, pathLevel: Int): ExtendedPrivateKey {
        val data = ByteArray(37)
        val writer = ByteArrayWriter(data)

        if (Index.isHardened(index)) {
            writer.concat(0.toByte())
            writer.concat(hdKey.key, 32)
        } else {
            writer.concat(hdKey.point)
        }
        writer.concatSer32(index)

        val I = hdKey.chainCode.hmacSha512(data)
        Arrays.fill(data, 0.toByte())

        val Il = head32(I)
        val Ir = tail32(I)

        val key = hdKey.key
        val parse256_Il = Il.parse256
        val ki = parse256_Il.add(key.parse256).mod(Secp256k1SC.n)

        if (parse256_Il >= Secp256k1SC.n || ki == BigInteger.ZERO) {
            return cKDpriv(index + 1, pathLevel)
        }

        Il.ser256(ki)

        return ExtendedPrivateKey(HdKey.Builder()
                .level(hdKey.level + 1)
                .crypto(hdKey.crypto)
                .neutered(false)
                .key(Il)
                .chainCode(Ir)
                .childNumber(index)
                .parentFingerprint(hdKey.calculateFingerPrint())
                .build())
    }

    override
    fun cKDpub(index: Int, pathLevel: Int): ExtendedPublicKey =
            cKDpriv(index, pathLevel).neuter()

    fun neuter(): ExtendedPublicKey = ExtendedPublicKey.from(hdKey)


    override
    fun derive(derivationPath: IntArray): ExtendedPrivateKey {
        return derive().derive(derivationPath)
    }

    override
    fun derive(derivationPath: CharSequence): ExtendedPrivateKey {
        return derive().derive(derivationPath)
    }

    override
    fun derive(derivationPath: Path): ExtendedPrivateKey {
        return derive().derive(derivationPath)
    }

    /* =========================== Private method =============================================== */
    private fun derive(): Derive<ExtendedPrivateKey> = derive(CKD_FUNCTION)

    private fun deriveWithCache(): Derive<ExtendedPrivateKey> = derive(newCacheOf(CKD_FUNCTION))

    private fun derive(ckdFunction: CkdFunction<ExtendedPrivateKey>): Derive<ExtendedPrivateKey> {
        return CkdFunctionDerive(hdKey.level, ckdFunction, this)
    }

}