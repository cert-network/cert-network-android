package com.dcen.wallet.dellet.core.bip32.network


enum class LitecoinNetwork : Network {
    MAIN_NET {
        override
        fun getPrivateVersion(): Int = 0x019d9cfe

        override
        fun getPublicVersion(): Int = 0x019da462

        override
        fun p2pkhVersion(): Byte = 0x30

        override
        fun p2shVersion(): Byte = 0x32
    }
}