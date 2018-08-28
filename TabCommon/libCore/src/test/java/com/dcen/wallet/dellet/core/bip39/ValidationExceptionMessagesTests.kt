package com.dcen.wallet.dellet.core.bip39

import com.dcen.wallet.dellet.core.bip39.validation.InvalidChecksumException
import com.dcen.wallet.dellet.core.bip39.validation.InvalidWordCountException
import com.dcen.wallet.dellet.core.bip39.validation.UnexpectedWhiteSpaceException
import org.junit.Assert.assertEquals
import org.junit.Test

class ValidationExceptionMessagesTests {

    @Test
    @Throws(Exception::class)
    fun InvalidWordCountException_message() {
        assertEquals("Not a correct number of words", InvalidWordCountException().message)
    }

    @Test
    @Throws(Exception::class)
    fun InvalidChecksumException_message() {
        assertEquals("Invalid checksum", InvalidChecksumException().message)
    }

    @Test
    @Throws(Exception::class)
    fun UnexpectedWhiteSpaceException_message() {
        assertEquals("Unexpected whitespace", UnexpectedWhiteSpaceException().message)
    }

}