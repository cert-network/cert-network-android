package com.dcen.wallet.dellet.core.bip32

import com.dcen.wallet.dellet.core.bip32.ByteArrayWriter.Companion.head32
import com.dcen.wallet.dellet.core.bip32.ByteArrayWriter.Companion.tail32
import com.dcen.wallet.dellet.core.bip32.Secp256k1SC.gMultiplyAndAddPoint
import com.dcen.wallet.dellet.core.bip32.Secp256k1SC.pointSerP
import com.dcen.wallet.dellet.core.bip32.crypto.Crypto
import com.dcen.wallet.dellet.core.bip32.derivation.CkdFunction
import com.dcen.wallet.dellet.core.bip32.derivation.CkdFunctionDerive
import com.dcen.wallet.dellet.core.bip32.derivation.CkdFunctionResultCacheDecorator.Companion.newCacheOf
import com.dcen.wallet.dellet.core.bip32.derivation.Derive
import com.dcen.wallet.dellet.core.bip32.exception.IllegalCKDCallException
import com.dcen.wallet.dellet.core.util.Index.isHardened
import com.dcen.wallet.dellet.core.util.base58.Base58.base58Encode
import com.dcen.wallet.dellet.core.util.hmacSha512
import com.dcen.wallet.dellet.core.util.parse256
import org.bouncycastle.math.ec.ECPoint


/**
 * A BIP32 public key
 *
 * coin     | Address size        | Address encoding | Address creation
 * ---------+---------------------+------------------+------------------
 * Bitcoin  | 160 bits (20 bytes) | Base58Check      | RIPEMD160(SHA256(<public_key>)
 * Ethereum | 160 bits (20 bytes) | HEX*             | KECCAK256(<public_key>)
 *
 */

@Suppress("PrivatePropertyName")
class ExtendedPublicKey
internal constructor(
        private val hdKey: HdKey
) : Derive<ExtendedPublicKey>, CKDpub, ExtendedKey {

    companion object {
        internal fun from(hdKey: HdKey): ExtendedPublicKey {
            return ExtendedPublicKey(
                    HdKey.Builder()
                            .level(hdKey.level)
                            .crypto(hdKey.crypto)
                            .neutered(true)
                            .key(hdKey.point)
                            .keyUncompressed(hdKey.pointUncompress)
                            .parentFingerprint(hdKey.parentFingerprint)
                            .childNumber(hdKey.childNumber)
                            .chainCode(hdKey.chainCode)
                            .build())
        }
    }


    override
    val key: PublicKey
        get() = PublicKey(hdKey)


    override
    val chainCode: ByteArray
        get() = hdKey.chainCode


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

    override
    val extendedKeyByteArray: ByteArray
        get() = hdKey.serialize()

    override
    val extendedBase58: String
        get() = base58Encode(extendedKeyByteArray)

    val uncompressedKey: ByteArray
        get() = hdKey.keyUncompressed

    val address: String
        get() = hdKey.crypto?.address(hdKey.key, hdKey.keyUncompressed) ?: ""

    private val CKD_FUNCTION = object : CkdFunction<ExtendedPublicKey> {
        override
        fun deriveChildKey(parent: ExtendedPublicKey, childIndex: Int, level: Int): ExtendedPublicKey {
            return parent.cKDpub(childIndex, level)
        }
    }


    override
    fun cKDpub(index: Int, pathLevel: Int): ExtendedPublicKey {
        if (isHardened(index))
            throw IllegalCKDCallException("Cannot derive a hardened key from a public key")

        val parent = this.hdKey
        val kPar = parent.key

        val data = ByteArray(37)
        val writer = ByteArrayWriter(data)
        writer.concat(kPar, 33)
        writer.concatSer32(index)

        val I = parent.chainCode.hmacSha512(data)
        val Il = head32(I)
        val Ir = tail32(I)

        val parse256_Il = Il.parse256
        val ki: ECPoint = gMultiplyAndAddPoint(parse256_Il, kPar)

        if (parse256_Il >= Secp256k1SC.n || ki.isInfinity) {
            return cKDpub(index + 1, pathLevel)
        }

        val key = pointSerP(ki, true)

        return ExtendedPublicKey(
                HdKey.Builder()
                        .level(parent.level + 1)
                        .crypto(parent.crypto)
                        .neutered(true)
                        .parentFingerprint(parent.calculateFingerPrint())
                        .key(key)
                        .keyUncompressed(pointSerP(ki, false))
                        .chainCode(Ir)
                        .childNumber(index)
                        .build())
    }


    override
    fun toCrypto(otherCrypto: Crypto): ExtendedPublicKey {
        return if (otherCrypto === crypto) this else ExtendedPublicKey(
                hdKey.toBuilder()
                        .crypto(otherCrypto)
                        .build())
    }


    override
    fun derive(derivationPath: IntArray): ExtendedPublicKey {
        return derive().derive(derivationPath)
    }

    override
    fun derive(derivationPath: CharSequence): ExtendedPublicKey {
        return derive().derive(derivationPath)
    }

    override
    fun derive(derivationPath: Path): ExtendedPublicKey {
        return derive().derive(derivationPath)
    }

//    fun <Path> derive(derivationPath: Path, ): ExtendedPublicKey {
//        return derive().derive(derivationPath, this)
//    }

    /* =========================== Private method =============================================== */

    private fun derive(): Derive<ExtendedPublicKey> = derive(CKD_FUNCTION)

    private fun deriveWithCache(): Derive<ExtendedPublicKey> = derive(newCacheOf(CKD_FUNCTION))

    private fun derive(ckdFunction: CkdFunction<ExtendedPublicKey>): Derive<ExtendedPublicKey> {
        return CkdFunctionDerive(hdKey.level, ckdFunction, this)
    }

}