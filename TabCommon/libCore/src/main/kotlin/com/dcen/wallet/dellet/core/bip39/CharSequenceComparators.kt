package com.dcen.wallet.dellet.core.bip39

import java.util.*

internal enum class CharSequenceComparators : Comparator<CharSequence> {

    ALPHABETICAL {
        override
        fun compare(o1: CharSequence, o2: CharSequence): Int {
            val length1 = o1.length
            val length2 = o2.length
            val length = Math.min(length1, length2)
            for (i in 0 until length) {
                val compare = Character.compare(o1[i], o2[i])
                if (compare != 0) return compare
            }
            return Integer.compare(length1, length2)
        }
    }

}