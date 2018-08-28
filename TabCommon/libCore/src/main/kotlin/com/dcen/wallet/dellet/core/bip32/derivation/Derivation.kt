package com.dcen.wallet.dellet.core.bip32.derivation

import com.dcen.wallet.dellet.core.bip32.ExtendedKey

interface Derivation<Path> {

    /**
     * Traverse the nodes from the root key node to find the node referenced by the path.
     *
     * @param rootKey     The root of the path
     * @param path        The path to follow
     * @param ckdFunction Allows you to follow one link
     * @param <Key>       The type of node we are visiting
     * @return The final node found at the end of the path
    </Key> */
//    fun <Key> derive(rootKey: Key, path: Path, ckdFunction: CkdFunction<Key>): Key

    fun <KEY: ExtendedKey> derive(rootKey: KEY, path: Path?, ckdFunction: CkdFunction<KEY>): KEY
}