package com.dcen.wallet.dellet.core.sign.core

import com.dcen.wallet.dellet.core.bip32.Secp256k1SC
import com.dcen.wallet.dellet.core.util.Hex
import com.dcen.wallet.dellet.core.util.parse256
import com.dcen.wallet.dellet.core.util.subArray
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.params.ECPrivateKeyParameters
import org.bouncycastle.crypto.signers.ECDSASigner
import org.bouncycastle.crypto.signers.HMacDSAKCalculator
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey
import java.math.BigInteger
import java.security.KeyPair

/**
 * Elliptic Curve SECP-256k1 generated key pair.
 */
class ECKeyPair private constructor(val privateKey: BigInteger?, val publicKey: BigInteger?) {

    companion object {

        fun create(keyPair: KeyPair): ECKeyPair {
            val privateKey = keyPair.private as BCECPrivateKey
            val publicKey = keyPair.public as BCECPublicKey

            val privateKeyValue = privateKey.d
            val publicKeyBytes = publicKey.q.getEncoded(false)
            val publicKeyValue = if (publicKeyBytes.size > 64) {
                // Ethereum does not use encoded public keys like bitcoin - see
                // https://en.bitcoin.it/wiki/Elliptic_Curve_Digital_Signature_Algorithm for details
                // Additionally, as the first bit is a constant prefix (0x04) we ignore this value
                publicKeyBytes.subArray(1, publicKeyBytes.size).parse256
            } else {
                publicKeyBytes.parse256
            }

            return ECKeyPair(privateKeyValue, publicKeyValue)
        }

        fun create(privateKey: BigInteger, publicKey: BigInteger = BigInteger.ZERO): ECKeyPair {
            if (publicKey.compareTo(BigInteger.ZERO) == 0) {
                val publicKeyByte: ByteArray = Secp256k1SC.pointSerP_gMultiply(privateKey, false)
                return ECKeyPair(privateKey, publicKeyByte.parse256)
            }
            return ECKeyPair(privateKey, publicKey)
        }

        fun create(privateKey: ByteArray): ECKeyPair {
            val publicKey: ByteArray = Secp256k1SC.pointSerP_gMultiply(privateKey.parse256, false)
            return ECKeyPair(privateKey.parse256, publicKey.parse256)
        }

        fun create(privateKey: String): ECKeyPair {
            return create(Hex.toBigInt(privateKey))
        }
    }


    /**
     * Sign a hash with the private key of this key pair.
     *
     * Pk = k*G = (X-coordinate primary key,Y-coordinate primary key,)
     *
     * r = X-coordinate primary key
     * s = k^-1*(Hash(message)+private_key*r)%p
     *
     * @param message   the hash to sign
     * @return  An [ECDSASignature] of the hash
     */
    fun sign(message: ByteArray): ECDSASignature {
        val signer = ECDSASigner(HMacDSAKCalculator(SHA256Digest()))
        val privateKey = ECPrivateKeyParameters(privateKey, Secp256k1SC.curveDomain)
        signer.init(true, privateKey)
        val components = signer.generateSignature(message)

        val r = components[0]
        val s = components[1]

        return ECDSASignature(r, s).toCanonicalized()
    }

    override
    fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ECKeyPair

        if (privateKey != other.privateKey) return false
        if (publicKey != other.publicKey) return false

        return true
    }

    override
    fun hashCode(): Int {
        var result = privateKey?.hashCode() ?: 0
        result = 31 * result + (publicKey?.hashCode() ?: 0)
        return result
    }


}
