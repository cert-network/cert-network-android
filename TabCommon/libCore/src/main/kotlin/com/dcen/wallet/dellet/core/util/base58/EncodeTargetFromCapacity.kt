package com.dcen.wallet.dellet.core.util.base58

interface EncodeTargetFromCapacity {

    fun withCapacity(characters: Int): EncodeTarget
}