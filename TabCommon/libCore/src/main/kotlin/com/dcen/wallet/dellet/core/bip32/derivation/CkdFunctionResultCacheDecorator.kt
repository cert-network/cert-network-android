package com.dcen.wallet.dellet.core.bip32.derivation

import java.util.*

/**
 * Non-thread safe result cache for ckd functions.
 *
 *
 * If the same child of the same parent is requested a second time, the original result will be returned.
 *
 * @param <Key> Key Node type.
</Key> */
class CkdFunctionResultCacheDecorator<Key>
private constructor(
        private val decoratedCkdFunction: CkdFunction<Key>
) : CkdFunction<Key> {

    companion object {
        @JvmStatic
        fun <Key> newCacheOf(decorated: CkdFunction<Key>): CkdFunction<Key> {
            return CkdFunctionResultCacheDecorator(decorated)
        }
    }


    private val cache = HashMap<Key, HashMap<Int, Key>>()

    override
    fun deriveChildKey(parent: Key, childIndex: Int, level: Int): Key {
        val mapForParent = getMapOf(parent)
        var child: Key? = mapForParent[childIndex]
        if (child == null) {
            child = decoratedCkdFunction.deriveChildKey(parent, childIndex, level)
            mapForParent[childIndex] = child
        }
        return child!!
    }

    private fun getMapOf(parentKey: Key): MutableMap<Int, Key> {
        var mapForParent: HashMap<Int, Key>? = cache[parentKey]
        if (mapForParent == null) {
            mapForParent = HashMap()
            cache[parentKey] = mapForParent
        }
        return mapForParent
    }

}