package com.dcen.wallet.dellet.core.sign.core

import com.dcen.wallet.dellet.core.bip32.Secp256k1SC
import com.dcen.wallet.dellet.core.util.parse256
import com.dcen.wallet.dellet.core.util.toPositiveInt
import org.bouncycastle.asn1.x9.X9IntegerConverter
import org.bouncycastle.math.ec.ECAlgorithms
import org.bouncycastle.math.ec.ECPoint
import org.bouncycastle.math.ec.custom.sec.SecP256K1Curve
import java.math.BigInteger
import java.security.SignatureException
import java.util.*
import kotlin.experimental.and

/**
 *
 * Transaction signing logic.
 *
 * Adapted from the
 * [BitcoinJ ECKey](https://github.com/bitcoinj/bitcoinj/blob/master/core/src/main/java/org/bitcoinj/core/ECKey.java) implementation.
 */
class Sign {

    companion object {
        const val COMPRESS_INDEX = 0
        const val UNCOMPRESS_INDEX = 1
    }

    fun signMessage(messageHash: ByteArray, keyPair: ECKeyPair): SignatureData {
        val publicKey = keyPair.publicKey

        val sig: ECDSASignature = keyPair.sign(messageHash)
        // Now we have to work backwards to figure out the recId needed to recover the signature.
        var recId = -1
        for (i in 0..3) {
            val k = recoverFromSignature(i, sig, messageHash)?.get(UNCOMPRESS_INDEX)
            if (k != null && k == publicKey) {
                recId = i
                break
            }
        }
        if (recId == -1) {
            throw RuntimeException(
                    "Could not construct a recoverable key. This should never happen.")
        }

        val headerByte = recId + 27

        // 1 header + 32 bytes for R + 32 bytes for S
        val v = headerByte.toByte()
        val r = toBytesPadded(sig.r, 32)
        val s = toBytesPadded(sig.s, 32)

        return SignatureData(v, r, s)
    }

    /**
     *
     * Given the components of a signature and a selector value, recover and return the public
     * key that generated the signature according to the algorithm in SEC1v2 section 4.1.6.
     *
     *
     *
     * The recId is an index from 0 to 3 which indicates which of the 4 possible keys is the
     * correct one. Because the key recovery operation yields multiple potential keys, the correct
     * key must either be stored alongside the
     * signature, or you must be willing to try each recId in turn until you find one that outputs
     * the key you are expecting.
     *
     *
     *
     * If this method returns null it means recovery was not possible and recId should be
     * iterated.
     *
     *
     *
     * Given the above two points, a correct usage of this method is inside a for loop from
     * 0 to 3, and if the output is null OR a key that is not the one you expect, you try again
     * with the next recId.
     *
     * @param recId   Which possible key to recover.
     * @param sig     the R and S components of the signature, wrapped.
     * @param message Hash of the data that was signed.
     * @return An array of ECKey containing only the public part (compress, uncompress), or null if recovery wasn't possible.
     *
     */
    fun recoverFromSignature(recId: Int, sig: ECDSASignature, message: ByteArray): Array<BigInteger>? {
        if (recId < 0) throw RuntimeException("recId must be positive")
        if (sig.r.signum() < 0) throw RuntimeException("r must be positive")
        if (sig.s.signum() < 0) throw RuntimeException("s must be positive")

        // 1.0 For j from 0 to h   (h == recId here and the loop is outside this function)
        //   1.1 Let x = r + jn
        val n = Secp256k1SC.n  // Curve order.
        val i = BigInteger.valueOf(recId.toLong() / 2)
        val x = sig.r.add(i.multiply(n))
        //   1.2. Convert the integer x to an octet string X of length mlen using the conversion
        //        routine specified in Section 2.3.7, where mlen = ⌈(log2 p)/8⌉ or mlen = ⌈m/8⌉.
        //   1.3. Convert the octet string (16 set binary digits)||X to an elliptic curve point R
        //        using the conversion routine specified in Section 2.3.4. If this conversion
        //        routine outputs "invalid", then do another iteration of Step 1.
        //
        // More concisely, what these points mean is to use X as a compressed public key.
        val prime = SecP256K1Curve.q
        if (x >= prime) {
            // Cannot have point co-ordinates larger than this as everything takes place modulo Q.
            return null
        }
        // Compressed keys require you to know an extra bit of data about the y-coord as there are
        // two possibilities. So it's encoded in the recId.
        val R = decompressKey(x, recId and 1 == 1)
        //   1.4. If nR != point at infinity, then do another iteration of Step 1 (callers
        //        responsibility).
        if (!R.multiply(n).isInfinity) {
            return null
        }
        //   1.5. Compute e from M using Steps 2 and 3 of ECDSA signature verification.
        val e = message.parse256
        //   1.6. For k from 1 to 2 do the following.   (loop is outside this function via
        //        iterating recId)
        //   1.6.1. Compute a candidate public key as:
        //               Q = mi(r) * (sR - eG)
        //
        // Where mi(x) is the modular multiplicative inverse. We transform this into the following:
        //               Q = (mi(r) * s ** R) + (mi(r) * -e ** G)
        // Where -e is the modular additive inverse of e, that is z such that z + e = 0 (mod n).
        // In the above equation ** is point multiplication and + is point addition (the EC group
        // operator).
        //
        // We can find the additive inverse by subtracting e from zero then taking the mod. For
        // example the additive inverse of 3 modulo 11 is 8 because 3 + 8 mod 11 = 0, and
        // -3 mod 11 = 8.
        val eInv = BigInteger.ZERO.subtract(e).mod(n)
        val rInv = sig.r.modInverse(n)
        val srInv = rInv.multiply(sig.s).mod(n)
        val eInvrInv = rInv.multiply(eInv).mod(n)
        val q = ECAlgorithms.sumOfTwoMultiplies(Secp256k1SC.g, eInvrInv, R, srInv)

        val qBytesCompress = q.getEncoded(true)
        val qBytesUnCompress = q.getEncoded(false)
        return arrayOf(
                BigInteger(1, qBytesCompress),
                // We remove the prefix
                BigInteger(1, Arrays.copyOfRange(qBytesUnCompress, 1, qBytesUnCompress.size)))
    }

    /**
     * Decompress a compressed public key (x co-ord and low-bit of y-coord).
     */
    private fun decompressKey(xBN: BigInteger, yBit: Boolean): ECPoint {
        val x9 = X9IntegerConverter()
        val compEnc = x9.integerToBytes(xBN, 1 + x9.getByteLength(Secp256k1SC.curve))
        compEnc[0] = (if (yBit) 0x03 else 0x02).toByte()
        return Secp256k1SC.curve.decodePoint(compEnc)
    }

    /**
     * Given an arbitrary piece of text and an Ethereum messageHash signature encoded in bytes,
     * returns the public key that was used to sign it. This can then be compared to the expected
     * public key to determine if the signature was correct.
     *
     * @param messageHash       Encoded messageHash.
     * @param signatureData The messageHash signature components
     * @return the array of public key (compress, uncompress) used to sign the messageHash
     * @throws SignatureException If the public key could not be recovered or if there was a
     * signature format error.
     */
    @Throws(SignatureException::class)
    fun signedMessageToKey(
            messageHash: ByteArray, signatureData: SignatureData): Array<BigInteger> {
        val r = signatureData.r
        val s = signatureData.s
        if (r.size != 32) throw RuntimeException("r must be 32 bytes")
        if (s.size != 32) throw RuntimeException("s must be 32 bytes")

        val header = signatureData.v and 0xFF.toByte()
        // The header byte: 0x1B = first key with even y, 0x1C = first key with odd y,
        //                  0x1D = second key with even y, 0x1E = second key with odd y
        if (header < 27 || header > 34) {
            throw SignatureException("Header byte out of range: $header")
        }

        val sig = ECDSASignature(signatureData.r.parse256, signatureData.s.parse256)

        val recId = header - 27
        return recoverFromSignature(recId, sig, messageHash)
                ?: throw SignatureException("Could not recover public key from signature")
    }

    private fun toBytesPadded(value: BigInteger, length: Int): ByteArray {
        val result = ByteArray(length)
        val bytes = value.toByteArray()

        val bytesLength: Int
        val srcOffset: Int
        if (bytes[0] == 0.toByte()) {
            bytesLength = bytes.size - 1
            srcOffset = 1
        } else {
            bytesLength = bytes.size
            srcOffset = 0
        }

        if (bytesLength > length) {
            throw RuntimeException("Input is too large to put in byte array of size $length")
        }

        val destOffset = length - bytesLength
        System.arraycopy(bytes, srcOffset, result, destOffset, bytesLength)
        return result
    }


    class SignatureData(
            val v: Byte,
            val r: ByteArray,
            val s: ByteArray) {

        override
        fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as SignatureData

            if (v != other.v) return false
            if (!Arrays.equals(r, other.r)) return false
            if (!Arrays.equals(s, other.s)) return false

            return true
        }

        override
        fun hashCode(): Int {
            var result = v.toPositiveInt()
            result = 31 * result + Arrays.hashCode(r)
            result = 31 * result + Arrays.hashCode(s)
            return result
        }
    }
}
