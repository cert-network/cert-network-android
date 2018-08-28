package com.dcen.wallet.dellet.core.bip32.derivation

interface CkdFunction<KeyNode> {
    /**
     * Derives the child at the given index on the parent.
     *
     * @param parent     The parent to find the child of
     * @param childIndex The index of the child
     * @return the [KeyNode] for the child
     */
    fun deriveChildKey(parent: KeyNode, childIndex: Int, level: Int): KeyNode
}
