package com.dcen.wallet.dellet.core.bip39

import com.dcen.wallet.dellet.core.bip39.wordlists.English
import com.dcen.wallet.dellet.core.bip39.wordlists.French
import com.dcen.wallet.dellet.core.bip39.wordlists.Japanese

import org.junit.Test

class EnumValueValueOfCodeCoverageTests {

    @Throws(Exception::class)
    private fun superficialEnumCodeCoverage(enumClass: Class<out Enum<*>>) {
        for (o in enumClass.getMethod("values").invoke(null) as Array<*>) {
            enumClass.getMethod("valueOf", String::class.java).invoke(null, o.toString())
        }
    }

    @Test
    @Throws(Exception::class)
    fun forCodeCoverageOnly_allEnums() {
        superficialEnumCodeCoverage(English::class.java)
        superficialEnumCodeCoverage(Japanese::class.java)
        superficialEnumCodeCoverage(French::class.java)
        superficialEnumCodeCoverage(CharSequenceComparators::class.java)
        superficialEnumCodeCoverage(SpongyCastlePBKDF2WithHmacSHA512::class.java)
        superficialEnumCodeCoverage(JavaxPBKDF2WithHmacSHA512::class.java)
        superficialEnumCodeCoverage(Words::class.java)
    }
}