package com.dcen.wallet.dellet.core.bip32

/**
 * Created by「 The Khaeng 」on 06 Jul 2018 :)
 */
interface Path {

    val level:Int
    val parent: Path?
    val value: Int

}