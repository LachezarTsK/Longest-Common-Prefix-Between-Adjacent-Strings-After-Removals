
public class Solution {

    private static final int MIN_WORDS_TO_HAVE_ONE_PAIR_AFTER_REMOVAL = 3;

    private int[] longestCommonPrefixForward;
    private int[] longestCommonPrefixBackward;

    public int[] longestCommonPrefix(String[] words) {
        if (words.length < MIN_WORDS_TO_HAVE_ONE_PAIR_AFTER_REMOVAL) {
            return new int[words.length];
        }

        int[] commonPrefix = new int[words.length - 1];
        for (int i = 0; i < words.length - 1; ++i) {
            commonPrefix[i] = findLengthCommonPrefix(words[i], words[i + 1]);
        }

        longestCommonPrefixForward = new int[words.length];
        for (int i = 1; i < words.length; ++i) {
            longestCommonPrefixForward[i] = Math.max(longestCommonPrefixForward[i - 1], commonPrefix[i - 1]);
        }

        longestCommonPrefixBackward = new int[words.length];
        for (int i = words.length - 2; i >= 0; --i) {
            longestCommonPrefixBackward[i] = Math.max(longestCommonPrefixBackward[i + 1], commonPrefix[i]);
        }

        int[] longestCommonPrefix = new int[words.length];
        for (int i = 0; i < words.length; ++i) {
            longestCommonPrefix[i] = findLongestCommonPrefixForGivenWordsOrder(words, i);
        }

        return longestCommonPrefix;
    }

    private int findLengthCommonPrefix(String firstWord, String secondWord) {
        int length = Math.min(firstWord.length(), secondWord.length());
        int commonPrefixLength = 0;
        for (int i = 0; i < length; ++i) {
            if (firstWord.charAt(i) != secondWord.charAt(i)) {
                break;
            }
            ++commonPrefixLength;
        }
        return commonPrefixLength;
    }

    private int findLongestCommonPrefixForGivenWordsOrder(String[] words, int removed) {
        if (removed > 0 && removed < words.length - 1) {
            int commonPrefixAdjacentToRemoved = findLengthCommonPrefix(words[removed - 1], words[removed + 1]);
            return Math.max(commonPrefixAdjacentToRemoved,
                    Math.max(longestCommonPrefixForward[removed - 1], longestCommonPrefixBackward[removed + 1]));
        }
        if (removed == 0) {
            return longestCommonPrefixBackward[1];
        }
        return longestCommonPrefixForward[removed - 1];
    }
}
