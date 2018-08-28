package util

import com.dcen.wallet.dellet.core.bip39.WordList
import com.dcen.wallet.dellet.core.util.Func
import com.dcen.wallet.dellet.core.util.toRuntime
import util.Hex.toHex
import java.security.MessageDigest
import java.text.Normalizer


internal object WordListHashing {

    val WORD_COUNT = 2048

    fun hashWordList(wordList: WordList): String {
        val digest = toRuntime(object : Func<MessageDigest> {
            override
            fun run(): MessageDigest {
                return MessageDigest.getInstance("SHA-256")
            }
        })
        for (i in 0 until WORD_COUNT) {
            digest.update((wordList.getWord(i) + "\n").toByteArray())
        }
        digest.update(("" + wordList.getSpace()).toByteArray())
        return toHex(digest.digest())
    }

    fun hashWordListNormalized(wordList: WordList): String {
        return hashWordList(normalizeNFKD(wordList))
    }

    private fun normalizeNFKD(wordList: WordList): WordList {
        return object : WordList {
            override
            fun getSpace(): Char {
                return wordList.getSpace()
            }

            override
            fun getWord(index: Int): String {
                return Normalizer.normalize(wordList.getWord(index), Normalizer.Form.NFKD)
            }
        }
    }
}