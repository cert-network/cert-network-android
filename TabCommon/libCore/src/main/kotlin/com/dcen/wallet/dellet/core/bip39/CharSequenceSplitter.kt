package com.dcen.wallet.dellet.core.bip39

import android.support.annotation.VisibleForTesting
import java.util.*

@VisibleForTesting
internal class CharSequenceSplitter(
        private val separator1: Char,
        private val separator2: Char) {

    fun split(charSequence: CharSequence): List<CharSequence> {
        val list = LinkedList<CharSequence>()
        var start = 0
        val length = charSequence.length
        for (i in 0 until length) {
            val c = charSequence[i]
            if (c == separator1 || c == separator2) {
                list.add(charSequence.subSequence(start, i))
                start = i + 1
            }
        }
        list.add(charSequence.subSequence(start, length))
        return list
    }
}