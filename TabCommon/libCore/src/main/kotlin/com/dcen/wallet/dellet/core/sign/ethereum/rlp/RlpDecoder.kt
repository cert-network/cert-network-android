package com.dcen.wallet.dellet.core.sign.ethereum.rlp

import com.dcen.wallet.dellet.core.util.and
import com.dcen.wallet.dellet.core.util.toPositiveInt
import java.util.*

/**
 *
 * Recursive Length Prefix (RLP) decoder.
 *
 * For the specification, refer to p16 of the [
 * yellow paper](http://gavwood.com/paper.pdf) and [here](https://github.com/ethereum/wiki/wiki/RLP).
 */
object RlpDecoder {

    /**
     * [0x80]
     * If a string is 0-55 bytes long, the RLP encoding consists of a single
     * byte with value 0x80 plus the length of the string followed by the
     * string. The range of the first byte is thus [0x80, 0xb7].
     */
    var OFFSET_SHORT_STRING = 0x80

    /**
     * [0xb7]
     * If a string is more than 55 bytes long, the RLP encoding consists of a
     * single byte with value 0xb7 plus the length of the length of the string
     * in binary form, followed by the length of the string, followed by the
     * string. For example, a length-1024 string would be encoded as
     * \xb9\x04\x00 followed by the string. The range of the first byte is thus
     * [0xb8, 0xbf].
     */
    var OFFSET_LONG_STRING = 0xb7

    /**
     * [0xc0]
     * If the total payload of a list (i.e. the combined length of all its
     * items) is 0-55 bytes long, the RLP encoding consists of a single byte
     * with value 0xc0 plus the length of the list followed by the concatenation
     * of the RLP encodings of the items. The range of the first byte is thus
     * [0xc0, 0xf7].
     */
    var OFFSET_SHORT_LIST = 0xc0

    /**
     * [0xf7]
     * If the total payload of a list is more than 55 bytes long, the RLP
     * encoding consists of a single byte with value 0xf7 plus the length of the
     * length of the list in binary form, followed by the length of the list,
     * followed by the concatenation of the RLP encodings of the items. The
     * range of the first byte is thus [0xf8, 0xff].
     */
    var OFFSET_LONG_LIST = 0xf7


    /**
     * Parse wire byte[] message into RLP elements.
     *
     * @param rlpEncoded - RLP encoded byte-array
     * @return recursive RLP structure
     */
    @JvmStatic
    fun decode(rlpEncoded: ByteArray): RlpList {
        val rlpList = RlpList(ArrayList())
        traverse(rlpEncoded, 0, rlpEncoded.size, rlpList)
        return rlpList
    }

    private fun traverse(data: ByteArray?, startPos: Int, endPos: Int, rlpList: RlpList) {
        var tmpStartPos = startPos

        try {
            if (data == null || data.isEmpty()) {
                return
            }

            while (tmpStartPos < endPos) {

                val prefix = data[tmpStartPos] and 0xff

                when {
                    prefix < OFFSET_SHORT_STRING -> {

                        // 1. the data is a string if the range of the
                        // first byte(i.e. prefix) is [0x00, 0x7f],
                        // and the string is the first byte itself exactly;

                        val rlpData: ByteArray = byteArrayOf(prefix.toByte())
                        rlpList.values.add(RlpString.create(rlpData))
                        tmpStartPos += 1

                    }
                    prefix == OFFSET_SHORT_STRING -> {

                        // null
                        rlpList.values.add(RlpString.create(ByteArray(0)))
                        tmpStartPos += 1

                    }
                    prefix in (OFFSET_SHORT_STRING + 1)..OFFSET_LONG_STRING -> {

                        // 2. the data is a string if the range of the
                        // first byte is [0x80, 0xb7], and the string
                        // which length is equal to the first byte minus 0x80
                        // follows the first byte;

                        val strLen = (prefix - OFFSET_SHORT_STRING).toByte()

                        val rlpData = ByteArray(strLen.toPositiveInt())
                        System.arraycopy(data, tmpStartPos + 1, rlpData, 0, strLen.toPositiveInt())

                        rlpList.values.add(RlpString.create(rlpData))
                        tmpStartPos += 1 + strLen.toPositiveInt()

                    }
                    prefix in (OFFSET_LONG_STRING + 1)..(OFFSET_SHORT_LIST - 1) -> {

                        // 3. the data is a string if the range of the
                        // first byte is [0xb8, 0xbf], and the length of the
                        // string which length in bytes is equal to the
                        // first byte minus 0xb7 follows the first byte,
                        // and the string follows the length of the string;

                        val lenOfStrLen = (prefix - OFFSET_LONG_STRING).toByte()
                        val strLen = calcLength(lenOfStrLen.toPositiveInt(), data, tmpStartPos)

                        // now we can parse an item for data[1]..data[length]
                        val rlpData = ByteArray(strLen)
                        System.arraycopy(data, tmpStartPos + lenOfStrLen.toPositiveInt() + 1, rlpData, 0, strLen)

                        rlpList.values.add(RlpString.create(rlpData))
                        tmpStartPos += lenOfStrLen.toPositiveInt() + strLen + 1

                    }
                    prefix in OFFSET_SHORT_LIST..OFFSET_LONG_LIST -> {

                        // 4. the data is a list if the range of the
                        // first byte is [0xc0, 0xf7], and the concatenation of
                        // the RLP encodings of all items of the list which the
                        // total payload is equal to the first byte minus 0xc0 follows the first byte;

                        val listLen = (prefix - OFFSET_SHORT_LIST).toByte()

                        val newLevelList = RlpList(ArrayList())
                        traverse(data, tmpStartPos + 1, tmpStartPos + listLen.toPositiveInt() + 1, newLevelList)
                        rlpList.values.add(newLevelList)

                        tmpStartPos += 1 + listLen.toPositiveInt()

                    }
                    prefix > OFFSET_LONG_LIST -> {

                        // 5. the data is a list if the range of the
                        // first byte is [0xf8, 0xff], and the total payload of the
                        // list which length is equal to the
                        // first byte minus 0xf7 follows the first byte,
                        // and the concatenation of the RLP encodings of all items of
                        // the list follows the total payload of the list;

                        val lenOfListLen: Byte = (prefix - OFFSET_LONG_LIST).toByte()
                        val listLen = calcLength(lenOfListLen.toPositiveInt(), data, tmpStartPos)

                        val newLevelList = RlpList(ArrayList())
                        traverse(data, tmpStartPos + lenOfListLen.toPositiveInt() + 1,
                                tmpStartPos + lenOfListLen.toPositiveInt() + listLen + 1, newLevelList)
                        rlpList.values.add(newLevelList)

                        tmpStartPos += lenOfListLen.toPositiveInt() + listLen + 1
                    }
                }

            }
        } catch (e: Exception) {
            throw RuntimeException("RLP wrong encoding", e)
        }

    }

    private fun calcLength(lengthOfLength: Int, data: ByteArray, pos: Int): Int {
        var pow = (lengthOfLength - 1).toByte()
        var length = 0
        for (i in 1..lengthOfLength) {
            length += (data[pos + i] and 0xff) shl (8 * pow)
            pow--
        }
        return length
    }

}
