package com.dcen.wallet.dellet.core.bip39.testjson

import com.google.gson.annotations.SerializedName

class TestVector {
    var mnemonic: String = ""
    var passphrase: String = ""
    var seed: String = ""
    var entropy: String = ""
    @SerializedName("bip32_xprv")
    var bip32Xprv: String = ""
}
