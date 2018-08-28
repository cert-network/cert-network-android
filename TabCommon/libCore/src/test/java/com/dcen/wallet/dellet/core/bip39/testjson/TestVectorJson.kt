package com.dcen.wallet.dellet.core.bip39.testjson

import util.Resources
import com.google.gson.annotations.SerializedName
import org.junit.Assert.assertEquals

class TestVectorJson {
    @SerializedName("data")
    var vectors: Array<TestVector> = arrayOf()

    companion object {

        fun loadJapanese(): TestVectorJson {
            val data = Resources.loadJsonResource("bip39_japanese_test_vectors.json", TestVectorJson::class.java)
            assertEquals(24, data.vectors.size.toLong())
            return data
        }

        fun loadFrench(): TestVectorJson {
            val data = Resources.loadJsonResource("bip39_french_test_vectors.json", TestVectorJson::class.java)
            assertEquals(18, data.vectors.size.toLong())
            return data
        }

        fun loadSpanish(): TestVectorJson {
            val data = Resources.loadJsonResource("bip39_spanish_test_vectors.json", TestVectorJson::class.java)
            assertEquals(18, data.vectors.size.toLong())
            return data
        }
    }
}