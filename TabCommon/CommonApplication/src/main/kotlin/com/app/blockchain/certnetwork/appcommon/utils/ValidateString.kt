package com.app.blockchain.certnetwork.appcommon.utils

import android.support.annotation.IntDef
import com.app.blockchain.certnetwork.common.extension.isNotMobileNumber

/**
 * Created by「 The Khaeng 」on 27 Mar 2018 :)
 */

const val VALIDATE = 0x000001
const val INVALIDATE = 0x000002

const val INVALIDATE_EMPTY = 0x000010 or INVALIDATE

const val INVALIDATE_MOBILE_NUMBER_FORMAT = 0x000100 or INVALIDATE



const val test: Long = 0xfffffffffffffff + 0xfffffffffffffff

@IntDef(VALIDATE,
        INVALIDATE,
        INVALIDATE_EMPTY,
        INVALIDATE_MOBILE_NUMBER_FORMAT )
@Retention(AnnotationRetention.SOURCE)
annotation class ValidateString

fun String.validateEmpty(): Int {
    if (this.isEmpty()) return INVALIDATE_EMPTY
    return VALIDATE
}



fun String.validateMobileNumber(): Int {
    if (this.isEmpty()) return INVALIDATE_EMPTY
    if (this.isNotMobileNumber()) return INVALIDATE_MOBILE_NUMBER_FORMAT
    return VALIDATE
}


fun @receiver:ValidateString Int.isValidate(): Boolean {
    return this and VALIDATE == VALIDATE
}

fun @receiver:ValidateString Int.isInvalidate(): Boolean {
    return this and INVALIDATE == INVALIDATE
}




