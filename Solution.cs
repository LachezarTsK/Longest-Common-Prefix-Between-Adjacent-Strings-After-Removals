
using System;

public class Solution
{
    private static readonly int MIN_WORDS_TO_HAVE_ONE_PAIR_AFTER_REMOVAL = 3;

    private int[]? longestCommonPrefixForward;
    private int[]? longestCommonPrefixBackward;

    public int[] LongestCommonPrefix(string[] words)
    {
        if (words.Length < MIN_WORDS_TO_HAVE_ONE_PAIR_AFTER_REMOVAL)
        {
            return new int[words.Length];
        }

        int[] commonPrefix = new int[words.Length - 1];
        for (int i = 0; i < words.Length - 1; ++i)
        {
            commonPrefix[i] = FindLengthCommonPrefix(words[i], words[i + 1]);
        }

        longestCommonPrefixForward = new int[words.Length];
        for (int i = 1; i < words.Length; ++i)
        {
            longestCommonPrefixForward[i] = Math.Max(longestCommonPrefixForward[i - 1], commonPrefix[i - 1]);
        }

        longestCommonPrefixBackward = new int[words.Length];
        for (int i = words.Length - 2; i >= 0; --i)
        {
            longestCommonPrefixBackward[i] = Math.Max(longestCommonPrefixBackward[i + 1], commonPrefix[i]);
        }

        int[] longestCommonPrefix = new int[words.Length];
        for (int i = 0; i < words.Length; ++i)
        {
            longestCommonPrefix[i] = FindLongestCommonPrefixForGivenWordsOrder(words, i);
        }

        return longestCommonPrefix;
    }

    private int FindLengthCommonPrefix(string firstWord, string secondWord)
    {
        int length = Math.Min(firstWord.Length, secondWord.Length);
        int commonPrefixLength = 0;
        for (int i = 0; i < length; ++i)
        {
            if (firstWord[i] != secondWord[i])
            {
                break;
            }
            ++commonPrefixLength;
        }
        return commonPrefixLength;
    }

    private int FindLongestCommonPrefixForGivenWordsOrder(string[] words, int removed)
    {
        if (removed > 0 && removed < words.Length - 1)
        {
            int commonPrefixAdjacentToRemoved = FindLengthCommonPrefix(words[removed - 1], words[removed + 1]);
            return Math.Max(commonPrefixAdjacentToRemoved,
                    Math.Max(longestCommonPrefixForward![removed - 1], longestCommonPrefixBackward![removed + 1]));
        }
        if (removed == 0)
        {
            return longestCommonPrefixBackward![1];
        }
        return longestCommonPrefixForward![removed - 1];
    }
}
