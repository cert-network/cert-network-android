package com.dcen.wallet.dellet.core.sign.ethereum.rlp

import java.util.*

/**
 * RLP list type.
 */
class RlpList : RlpType {
    val values: MutableList<RlpType>

    constructor(vararg values: RlpType) {
        this.values = Arrays.asList(*values)
    }

    constructor(values: List<RlpType>) {
        this.values = values.toMutableList()
    }
}
