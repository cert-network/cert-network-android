package com.dcen.wallet.dellet.core.bip39.testjson

import util.Resources

import org.junit.Assert.assertEquals

class EnglishJson {
    var english: Array<Array<String>> = arrayOf()

    companion object {
        fun load(): EnglishJson {
            val data = Resources.loadJsonResource("bip39_english_test_vectors.json", EnglishJson::class.java)
            assertEquals(24, data.english.size.toLong())
            return data
        }
    }
}
