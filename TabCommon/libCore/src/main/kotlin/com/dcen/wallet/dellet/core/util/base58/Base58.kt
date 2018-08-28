package com.dcen.wallet.dellet.core.util.base58

/**
 * Class for encoding byte arrays to base58.
 * Suitable for small data arrays as the algorithm is O(n^2).
 * Don't share instances across threads.
 * Static methods are threadsafe however.
 */
object Base58 {

    private val working = ThreadLocal<EncoderDecoder>()

    private val threadSharedBase58: EncoderDecoder
        get() {
            var base58: EncoderDecoder? = working.get()
            if (base58 == null) {
                base58 = newInstance()
                working.set(base58)
            }
            return base58
        }


    @JvmStatic
    fun newInstanceWithBuffer(workingBuffer: WorkingBuffer): GeneralEncoderDecoder {
        return Base58EncoderDecoder(workingBuffer)
    }

    @JvmStatic
    fun newInstance(): EncoderDecoder {
        return newInstanceWithBuffer(ByteArrayWorkingBuffer())
    }

    @JvmStatic
    fun newSecureInstance(): SecureEncoderDecoder {
        return newInstanceWithBuffer(SecureWorkingBuffer())
    }

    /**
     * Encodes given bytes as a number in base58.
     * Threadsafe, uses an instance per thread.
     *
     * @param bytes bytes to encode
     * @return base58 string representation
     */
    @JvmStatic
    fun base58Encode(bytes: ByteArray): String {
        return threadSharedBase58.encode(bytes)
    }

    /**
     * Decodes given bytes as a number in base58.
     * Threadsafe, uses an instance per thread.
     *
     * @param base58 string to decode
     * @return number as bytes
     */
    @JvmStatic
    fun base58Decode(base58: CharSequence): ByteArray {
        return threadSharedBase58.decode(base58)
    }
}