package com.app.blockchain.certnetwork.common.extension

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

fun <T : Any> T?.throwIfNull(message: String? = "object is null") {
    if (this == null) {
        throw IllegalArgumentException(message)
    }
}

fun String?.throwIfEmpty(message: String? = "string is null or empty") {
    if (this == null || this.isEmpty()) {
        throw IllegalArgumentException(message)
    }
}

fun <T : Any> Collection<T>?.throwIfEmpty(message: String? = "collection is null or empty") {
    if (this == null || this.isEmpty()) {
        throw IllegalArgumentException(message)
    }
}

fun Boolean.throwIfFalse(message: String? = "condition is false") {
    if (!this) {
        throw IllegalArgumentException(message)
    }
}

fun Boolean.throwIfTrue(message: String? = "condition is true") {
    if (this) {
        throw IllegalArgumentException(message)
    }
}