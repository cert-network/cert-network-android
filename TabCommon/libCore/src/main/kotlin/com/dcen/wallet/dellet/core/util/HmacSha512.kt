@file:JvmName("HmacSha512")
package com.dcen.wallet.dellet.core.util


import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


private val HMAC_SHA512 = "HmacSHA512"

fun ByteArray.hmacSha512(seed: ByteArray): ByteArray {
    return initialize(this)
            .doFinal(seed)
}

private fun initialize(byteKey: ByteArray): Mac {
    val hmacSha512 = getInstance(HMAC_SHA512)
    val keySpec = SecretKeySpec(byteKey, HMAC_SHA512)
    toRuntime(object : Action {
        override
        fun run() {
            hmacSha512.init(keySpec)
        }

    })
    return hmacSha512
}

private fun getInstance(macAlgorithm: String): Mac {
    return toRuntime(object : Func<Mac> {
        override
        fun run(): Mac {
            return Mac.getInstance(macAlgorithm)
        }

    })
}