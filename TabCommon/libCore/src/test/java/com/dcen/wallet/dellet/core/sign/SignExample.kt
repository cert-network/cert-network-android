package com.dcen.wallet.dellet.core.sign

import com.dcen.wallet.dellet.core.sign.core.ECKeyPair
import java.math.BigInteger

/**
 * Created by「 The Khaeng 」on 07 Aug 2018 :)
 */
object SignExample {
    val PRIVATE_KEY_STR = "a392604efc2fad9c0b3da43b5f698a2e3f270f170d859912be0d54742275c5f6"
    val PUBLIC_KEY_STR = "506bc1dc099358e5137292f4efdd57e400f29ba5132aa5d12b18dac1c1f6aaba645c0b7b58158babbfa6c6cd5a48aa7340a8749176b120e8516216787a13dc76"
    val PRIVATE_KEY = BigInteger(PRIVATE_KEY_STR, 16)
    val PUBLIC_KEY = BigInteger(PUBLIC_KEY_STR, 16)
    val KEY_PAIR = ECKeyPair.create(PRIVATE_KEY)
    val ADDRESS = "0xef678007D18427E6022059Dbc264f27507CD1ffC"
}