package com.dcen.wallet.dellet.core.util.base58

class BadCharacterException(charAtI: Char) : RuntimeException("Bad character in base58 string, '$charAtI'")