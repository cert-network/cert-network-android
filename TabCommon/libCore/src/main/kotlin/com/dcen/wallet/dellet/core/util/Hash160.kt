@file:JvmName("Hash160")

package com.dcen.wallet.dellet.core.util

import org.spongycastle.crypto.digests.RIPEMD160Digest

/**
 * Created by「 The Khaeng 」on 03 Jul 2018 :)
 */
@Suppress("MayBeConstant")
private val RIPEMD160_DIGEST_SIZE = 20

val ByteArray.hash160: ByteArray get() = this.sha256.ripemd160

fun ByteArray.hash160into(offset: Int, bytes: ByteArray) {
    bytes.sha256.ripemd160into(this, offset)
}

val ByteArray.ripemd160: ByteArray
    get() {
        val output = ByteArray(RIPEMD160_DIGEST_SIZE)
        this.ripemd160into(output, 0)
        return output
    }

fun ByteArray.ripemd160into(target: ByteArray, offset: Int) {
    val digest = RIPEMD160Digest()
    digest.update(this, 0, this.size)
    digest.doFinal(target, offset)
}
