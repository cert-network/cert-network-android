@file:JvmName("Sha")

package com.dcen.wallet.dellet.core.util

import org.bouncycastle.jcajce.provider.digest.Keccak
import java.security.MessageDigest


/**
 * Created by「 The Khaeng 」on 03 Jul 2018 :)
 */


val ByteArray.keccak256: ByteArray get() = this.keccak256(0, this.size)


fun ByteArray.keccak256(offset: Int, length: Int): ByteArray {
    val kecc = Keccak.Digest256()
    kecc.update(this, offset, length)
    return kecc.digest()
}

val String.keccak256String: String get() = Hex.toHexString(this.toByteArray().keccak256)

val ByteArray.sha256: ByteArray get() = this.sha256(0, this.size)

fun ByteArray.sha256(offset: Int , length: Int): ByteArray {
    val digest = toRuntime(object : Func<MessageDigest> {
        override
        fun run(): MessageDigest {
            return MessageDigest.getInstance("SHA-256")
        }

    })
    digest.update(this, offset, length)
    return digest.digest()
}

val ByteArray.sha256Twice: ByteArray get() = this.sha256Twice(0,this.size)

fun ByteArray.sha256Twice(offset: Int, length: Int): ByteArray {
    val digest = toRuntime(object : Func<MessageDigest> {
        override
        fun run(): MessageDigest {
            return MessageDigest.getInstance("SHA-256")
        }

    })
    digest.update(this, offset, length)
    digest.update(digest.digest())
    return digest.digest()
}

