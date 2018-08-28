package com.dcen.wallet.dellet.core.util

import android.text.TextUtils

/**
 * String utility functions.
 */
object Strings {

    // return src == null ? null : String.join(", ", src.toArray(new String[0]));
    fun toCsv(src: List<String>): String? = join(src, ", ")

    fun join(src: List<String>?, delimiter: String): String? =
            if (src == null) null else TextUtils.join(delimiter, src.toTypedArray())

    fun capitaliseFirstLetter(string: String?): String? =
            if (string == null || string.isEmpty()) {
                string
            } else {
                string.substring(0, 1).toUpperCase() + string.substring(1)
            }

    fun lowercaseFirstLetter(string: String?): String? =
            if (string == null || string.isEmpty()) {
                string
            } else {
                string.substring(0, 1).toLowerCase() + string.substring(1)
            }


    fun zeros(n: Int): String = repeat('0', n)

    fun repeat(value: Char, n: Int): String = String(CharArray(n)).replace("\u0000", value.toString())

    fun isEmpty(s: String?): Boolean = s == null || s.isEmpty()
}
