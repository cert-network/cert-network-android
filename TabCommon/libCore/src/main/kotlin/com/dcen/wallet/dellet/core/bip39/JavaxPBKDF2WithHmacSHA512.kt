package com.dcen.wallet.dellet.core.bip39

import com.dcen.wallet.dellet.core.util.Func
import com.dcen.wallet.dellet.core.util.toRuntime
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


/**
 * Not available in all Java implementations, for example will not find the implementation before Android API 26+.
 * See https://developer.android.com/reference/javax/crypto/SecretKeyFactory.html for more details.
 */
enum class JavaxPBKDF2WithHmacSHA512 : PBKDF2WithHmacSHA512 {
    INSTANCE;

    private val skf = pbkdf2WithHmacSHA512

    private val pbkdf2WithHmacSHA512: SecretKeyFactory
        get() = toRuntime(object : Func<SecretKeyFactory> {
            @Throws(Exception::class)
            override fun run(): SecretKeyFactory {
                return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
            }
        })

    override fun hash(chars: CharArray, salt: ByteArray): ByteArray {
        val spec = PBEKeySpec(chars, salt, 2048, 512)
        val encoded = generateSecretKey(spec).encoded
        spec.clearPassword()
        return encoded
    }

    private fun generateSecretKey(spec: PBEKeySpec): SecretKey {
        return toRuntime(object : Func<SecretKey> {
            @Throws(Exception::class)
            override fun run(): SecretKey {
                return skf.generateSecret(spec)
            }
        })
    }
}