package com.dcen.wallet.dellet.core.bip39.validation

class WordNotFoundException(
        val word: CharSequence,
        val suggestion1: CharSequence,
        val suggestion2: CharSequence) : Exception(String.format(
        "Word not found in word list \"%s\", suggestions \"%s\", \"%s\"",
        word,
        suggestion1,
        suggestion2))