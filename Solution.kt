
import kotlin.math.min
import kotlin.math.max

class Solution {
    private companion object {
        const val MIN_WORDS_TO_HAVE_ONE_PAIR_AFTER_REMOVAL = 3
    }

    private lateinit var longestCommonPrefixForward: IntArray
    private lateinit var longestCommonPrefixBackward: IntArray

    fun longestCommonPrefix(words: Array<String>): IntArray {
        if (words.size < MIN_WORDS_TO_HAVE_ONE_PAIR_AFTER_REMOVAL) {
            return IntArray(words.size)
        }

        val commonPrefix = IntArray(words.size - 1)
        for (i in 0..<words.size - 1) {
            commonPrefix[i] = findLengthCommonPrefix(words[i], words[i + 1])
        }

        longestCommonPrefixForward = IntArray(words.size)
        for (i in 1..<words.size) {
            longestCommonPrefixForward[i] = max(longestCommonPrefixForward[i - 1], commonPrefix[i - 1])
        }

        longestCommonPrefixBackward = IntArray(words.size)
        for (i in words.size - 2 downTo 0) {
            longestCommonPrefixBackward[i] = max(longestCommonPrefixBackward[i + 1], commonPrefix[i])
        }

        val longestCommonPrefix = IntArray(words.size)
        for (i in words.indices) {
            longestCommonPrefix[i] = findLongestCommonPrefixForGivenWordsOrder(words, i)
        }

        return longestCommonPrefix
    }

    private fun findLengthCommonPrefix(firstWord: String, secondWord: String): Int {
        val length = min(firstWord.length, secondWord.length)
        var commonPrefixLength = 0
        for (i in 0..<length) {
            if (firstWord[i] != secondWord[i]) {
                break
            }
            ++commonPrefixLength
        }
        return commonPrefixLength
    }

    private fun findLongestCommonPrefixForGivenWordsOrder(words: Array<String>, removed: Int): Int {
        if (removed > 0 && removed < words.size - 1) {
            val commonPrefixAdjacentToRemoved = findLengthCommonPrefix(words[removed - 1], words[removed + 1])
            return max(
                commonPrefixAdjacentToRemoved,
                max(longestCommonPrefixForward[removed - 1], longestCommonPrefixBackward[removed + 1])
            )
        }
        if (removed == 0) {
            return longestCommonPrefixBackward[1]
        }
        return longestCommonPrefixForward[removed - 1]
    }
}
