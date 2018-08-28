package util

object TestCharSequence {
    fun preventToStringAndSubSequence(sequence: CharSequence): CharSequence {
        return object : CharSequence {
            override
            val length: Int
                get() {
                    return sequence.length
                }

            override
            fun get(index: Int): Char {
                return sequence[index]
            }

            override
            fun equals(other: Any?): Boolean {
                return sequence == other
            }

            override
            fun hashCode(): Int {
                return sequence.hashCode()
            }

            override
            fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
                throw RuntimeException("subSequence Not Allowed")
            }

            override
            fun toString(): String {
                throw RuntimeException("toString Not Allowed")
            }
        }
    }

    fun preventToString(sequence: CharSequence): CharSequence {
        return object : CharSequence {
            override
            val length: Int
            get(){
                return sequence.length
            }

            override
            fun get(index: Int): Char {
                return sequence[index]
            }

            override
            fun equals(other: Any?): Boolean {
                return sequence == other
            }

            override
            fun hashCode(): Int {
                return sequence.hashCode()
            }

            override
            fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
                return preventToString(sequence.subSequence(startIndex, endIndex))
            }

            override fun toString(): String {
                throw RuntimeException("toString Not Allowed")
            }
        }
    }
}