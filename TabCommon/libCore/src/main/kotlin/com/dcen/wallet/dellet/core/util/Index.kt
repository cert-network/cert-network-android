package com.dcen.wallet.dellet.core.util

object Index {

    const val HARD = -0x80000000

    @JvmStatic
    fun hard(index: Int): Int {
        return index or HARD
    }

    @JvmStatic
    fun isHardened(i: Int): Boolean {
        return i and HARD != 0
    }
}