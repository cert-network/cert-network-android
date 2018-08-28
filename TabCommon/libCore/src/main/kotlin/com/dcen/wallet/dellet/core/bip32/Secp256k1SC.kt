package com.dcen.wallet.dellet.core.bip32

import org.bouncycastle.crypto.ec.CustomNamedCurves
import org.bouncycastle.crypto.params.ECDomainParameters
import org.bouncycastle.math.ec.ECCurve
import org.bouncycastle.math.ec.ECPoint
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils
import java.math.BigInteger

/**
 * sec(Standards for Efficient Cryptography) p256k1 (256 bits)
 * p = Finite Field Prime Number
 * g = Generator point (x,y)
 * n = prime number of points in the group
 *
 * Equation y2 = x3 + 7 (a = 0, b = 7)
 * Prime Field (p) = 2^256 - 2^32 - 2^9 - 2^8 - 2^7 - 2^6 - 2^4 - 1 =
 * Base point (G) = (79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798, 483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8)
 * Order (n) = FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141
 * Cofactor (h) = 01
 **/

object Secp256k1SC {

    private val CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1")
    val curveDomain = ECDomainParameters(
            CURVE_PARAMS.curve, CURVE_PARAMS.g, CURVE_PARAMS.n, CURVE_PARAMS.h)
    val HALF_CURVE_ORDER = CURVE_PARAMS.n.shiftRight(1)

    @JvmStatic
    val n: BigInteger
        get() = curveDomain.n

    @JvmStatic
    val g: ECPoint
        get() = curveDomain.g

    @JvmStatic
    val curve: ECCurve
        get() = curveDomain.curve

    @JvmStatic
    fun pointSerP(point: ECPoint, compressed: Boolean): ByteArray {
        var result = point.getEncoded(compressed)

        if (!compressed) {
            // Ethereum does not use encoded public keys like bitcoin - see
            // https://en.bitcoin.it/wiki/Elliptic_Curve_Digital_Signature_Algorithm for details
            // Additionally, as the first bit is a constant prefix (0x04) we ignore this value
            result = removeUncompressPrefix(result)
        }
        return result
    }

    fun removeUncompressPrefix(key: ByteArray): ByteArray {
        return if (key.size > 64) {
            ByteUtils.subArray(key, 1, key.size)
        } else {
            key
        }
    }

    @JvmStatic
    fun pointSerP_gMultiply(p: BigInteger, compressed: Boolean): ByteArray =
            pointSerP(gMultiply(p), compressed)

    @JvmStatic
    fun gMultiplyAndAddPoint(p: BigInteger, toAdd: ByteArray): ECPoint =
            gMultiply(p).add(decode(toAdd))

    @JvmStatic
    private fun decode(toAdd: ByteArray): ECPoint =
            curve.decodePoint(toAdd)

    @JvmStatic
    private fun gMultiply(p: BigInteger): ECPoint =
            g.multiply(p)

}