package com.app.blockchain.certnetwork.common.extension

/**
 * Created by「 The Khaeng 」on 06 Apr 2018 :)
 */

fun Int?.notnull(): Int = this ?: 0
fun Float?.notnull(): Float = this ?: 0f
fun Long?.notnull(): Long = this ?: 0L
fun String?.notnull(): String = this ?: ""
fun Boolean?.notnull(): Boolean = this ?: false
fun <T> List<T>?.notnull(): List<T> = this ?: listOf()
fun <A,B> Map<A,B>?.notnull(): Map<A,B> = this ?: mapOf()
