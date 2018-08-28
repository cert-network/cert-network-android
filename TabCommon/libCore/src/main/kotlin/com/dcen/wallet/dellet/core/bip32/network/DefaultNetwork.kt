package com.dcen.wallet.dellet.core.bip32.network

import com.dcen.wallet.dellet.core.bip32.network.Network


enum class DefaultNetwork : Network {
    MAIN_NET {
        override
        fun getPrivateVersion(): Int = 0x488ade4

        override
        fun getPublicVersion(): Int = 0x0488b21e

        override
        fun p2pkhVersion(): Byte = 0

        override
        fun p2shVersion(): Byte = 0x05
    },

    TEST_NET {
        override
        fun getPrivateVersion(): Int = 0x4358394

        override
        fun getPublicVersion(): Int = 0x043587cf

        override
        fun p2pkhVersion(): Byte = 0x6f

        override
        fun p2shVersion(): Byte = 0xc4.toByte()
    }
}