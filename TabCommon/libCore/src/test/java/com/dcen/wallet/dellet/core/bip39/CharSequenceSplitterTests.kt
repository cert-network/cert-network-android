package com.dcen.wallet.dellet.core.bip39

import org.junit.Assert.assertEquals
import org.junit.Test

class CharSequenceSplitterTests {

    @Test
    fun empty_sequence() {
        val list = CharSequenceSplitter(' ', ' ').split("")
        assertEquals(1, list.size.toLong())
        assertEquals("", list[0].toString())
    }

    @Test
    fun sequence_containing_one() {
        val list = CharSequenceSplitter(' ', ' ').split("abc")
        assertEquals(1, list.size.toLong())
        assertEquals("abc", list[0].toString())
    }

    @Test
    fun two_items() {
        val list = CharSequenceSplitter(' ', ' ').split("abc def")
        assertEquals(2, list.size.toLong())
        assertEquals("abc", list[0].toString())
        assertEquals("def", list[1].toString())
    }

    @Test
    fun two_items_different_separator() {
        val list = CharSequenceSplitter('-', '-').split("abc-def")
        assertEquals(2, list.size.toLong())
        assertEquals("abc", list[0].toString())
        assertEquals("def", list[1].toString())
    }

    @Test
    fun just_separator() {
        val list = CharSequenceSplitter('-', '-').split("-")
        assertEquals(2, list.size.toLong())
        assertEquals("", list[0].toString())
        assertEquals("", list[1].toString())
    }

    @Test
    fun separator_at_end() {
        val list = CharSequenceSplitter('-', '-').split("a-b-c-")
        assertEquals(4, list.size.toLong())
        assertEquals("a", list[0].toString())
        assertEquals("b", list[1].toString())
        assertEquals("c", list[2].toString())
        assertEquals("", list[3].toString())
    }

    @Test
    fun two_separators_in_middle() {
        val list = CharSequenceSplitter('-', '-').split("a--b-c")
        assertEquals(4, list.size.toLong())
        assertEquals("a", list[0].toString())
        assertEquals("", list[1].toString())
        assertEquals("b", list[2].toString())
        assertEquals("c", list[3].toString())
    }

    @Test
    fun different_separators() {
        val list = CharSequenceSplitter('-', '+').split("a-b+c")
        assertEquals(3, list.size.toLong())
        assertEquals("a", list[0].toString())
        assertEquals("b", list[1].toString())
        assertEquals("c", list[2].toString())
    }

    @Test
    fun whiteBox_number_of_expected_calls() {
        val inner = "abc-def-123"
        val spy = Spy(inner)
        CharSequenceSplitter('-', '-').split(spy)
        assertEquals(1, spy.lengthCalls.toLong())
        assertEquals(inner.length.toLong(), spy.charAtCalls.toLong())
        assertEquals(3, spy.subSequenceCalls.toLong())
        assertEquals(0, spy.toStringCalls.toLong())
    }

    private inner class Spy(private val inner: CharSequence) : CharSequence {
        internal var lengthCalls: Int = 0
        internal var charAtCalls: Int = 0
        internal var subSequenceCalls: Int = 0
        internal var toStringCalls: Int = 0

        override
        val length: Int
            get() {
                lengthCalls++
                return inner.length
            }

        override
        fun get(index: Int): Char {
            charAtCalls++
            return inner[index]
        }

        override
        fun subSequence(start: Int, end: Int): CharSequence {
            subSequenceCalls++
            return inner.subSequence(start, end)
        }

        override
        fun toString(): String {
            toStringCalls++
            return super.toString()
        }
    }
}