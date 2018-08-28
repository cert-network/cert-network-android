package com.app.blockchain.certnetwork.common.extension

import java.util.regex.Pattern

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

fun String.quote(): String {
    return "'$this'"
}

fun String?.ifNotEmpty(block: (String) -> Unit) {
    if (this != null && this.isNotEmpty()) {
        block(this)
    }
}

fun CharSequence.isBlank(): Boolean {
    val len: Int = this.length
    if (len == 0) {
        return true
    }
    forEach { c ->
        if (!Character.isWhitespace(c)) {
            return false
        }
    }
    return true
}

fun String.trimAllWhitespace(): String {
    if (this.isEmpty()) {
        return this
    }
    val sb = StringBuilder(this)
    var index = 0
    while (sb.length > index) {
        if (Character.isWhitespace(sb[index])) {
            sb.deleteCharAt(index)
        } else {
            index++
        }
    }
    return sb.toString()
}

fun CharSequence.containsWhitespace(): Boolean {
    if (this.isEmpty()) {
        return false
    }
    forEach { c ->
        if (Character.isWhitespace(c)) {
            return true
        }
    }
    return false
}

fun String.isInt(): Boolean = Pattern.matches("\\d+", this)

fun String.isNotInt(): Boolean = !this.isInt()

fun String.isFloat(): Boolean = Pattern.matches("^([+-]?\\d+\\.?\\d+)\$", this)

fun String.isDecimal(): Boolean = this.isInt() || this.isFloat()

val String.factionLength: Int
    get() {
        if (this.isNotFloat()) return 0
        return this.split(".")[1].length
    }

fun String.isNotFloat(): Boolean = !this.isFloat()

fun String.removeNonNumeric(): String? = this.replace(",".toRegex(), "").replace("[^\\d|\\.]*$".toRegex(), "")

fun String?.isEmail(): Boolean {
    if (this != null) {
        val emailPattern = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
        )
        return emailPattern.matcher(this).matches()
    }
    return false
}

fun String?.isNotEmail(): Boolean = !this.isEmail()

fun String?.isMobileNumber(): Boolean =
        this != null && this.length > 5 && (Pattern.matches("\\d+", this) || Pattern.matches("\\+\\d+", this))

fun String?.isNotMobileNumber(): Boolean = !this.isMobileNumber()

fun String.isIdCard(): Boolean {
    if (this.length != 13) return false
    return try {
        var sum = 0
        for (i in 0..11) {
            sum += Integer.parseInt(this[i].toString()) * (13 - i)
        }
        this[12] - '0' == (11 - sum % 11) % 10
    } catch (e: NumberFormatException) {
        e.printStackTrace()
        false
    }

}

fun String.isNotIdCard(): Boolean = !this.isIdCard()

