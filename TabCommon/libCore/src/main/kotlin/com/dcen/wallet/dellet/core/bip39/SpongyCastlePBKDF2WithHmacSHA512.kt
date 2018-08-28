package com.dcen.wallet.dellet.core.bip39

import org.spongycastle.crypto.PBEParametersGenerator
import org.spongycastle.crypto.digests.SHA512Digest
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator
import org.spongycastle.crypto.params.KeyParameter

/**
 * This implementation is useful for older Java implementations, for example it is suitable for all Android API levels.
 */
enum class SpongyCastlePBKDF2WithHmacSHA512 : PBKDF2WithHmacSHA512 {
    INSTANCE;

    override
    fun hash(chars: CharArray, salt: ByteArray): ByteArray {
        val generator = PKCS5S2ParametersGenerator(SHA512Digest())
        generator.init(PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(chars), salt, 2048)
        val key = generator.generateDerivedMacParameters(512) as KeyParameter
        return key.key
    }
}