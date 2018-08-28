package com.dcen.wallet.dellet.core.bip39

interface WordList {

    /**
     * Get the space character for this language.
     *
     * @return a whitespace character.
     */
    fun getSpace(): Char

    /**
     * Get a word in the word list.
     *
     * @param index Index of word in the word list [0..2047] inclusive.
     * @return the word from the list.
     */
    fun getWord(index: Int): String
}