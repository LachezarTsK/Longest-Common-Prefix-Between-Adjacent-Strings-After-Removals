
package main

const MIN_WORDS_TO_HAVE_ONE_PAIR_AFTER_REMOVAL = 3

var longestCommonPrefixForward []int
var longestCommonPrefixBackward []int

func longestCommonPrefix(words []string) []int {
    if len(words) < MIN_WORDS_TO_HAVE_ONE_PAIR_AFTER_REMOVAL {
        return make([]int, len(words))
    }

    commonPrefix := make([]int, len(words) - 1)
    for i := range len(words) - 1 {
        commonPrefix[i] = findLengthCommonPrefix(words[i], words[i + 1])
    }

    longestCommonPrefixForward = make([]int, len(words))
    for i := 1; i < len(words); i++ {
        longestCommonPrefixForward[i] = max(longestCommonPrefixForward[i - 1], commonPrefix[i - 1])
    }

    longestCommonPrefixBackward = make([]int, len(words))
    for i := len(words) - 2; i >= 0; i-- {
        longestCommonPrefixBackward[i] = max(longestCommonPrefixBackward[i + 1], commonPrefix[i])
    }

    longestCommonPrefix := make([]int, len(words))
    for i := range words {
        longestCommonPrefix[i] = findLongestCommonPrefixForGivenWordsOrder(words, i)
    }

    return longestCommonPrefix
}

func findLengthCommonPrefix(firstWord string, secondWord string) int {
    length := min(len(firstWord), len(secondWord))
    commonPrefixLength := 0
    for i := range length {
        if firstWord[i] != secondWord[i] {
            break
        }
        commonPrefixLength++
    }
    return commonPrefixLength
}

func findLongestCommonPrefixForGivenWordsOrder(words []string, removed int) int {
    if removed > 0 && removed < len(words) - 1 {
        commonPrefixAdjacentToRemoved := findLengthCommonPrefix(words[removed - 1], words[removed + 1])
        return max(commonPrefixAdjacentToRemoved,
                max(longestCommonPrefixForward[removed - 1], longestCommonPrefixBackward[removed + 1]))
    }
    if removed == 0 {
        return longestCommonPrefixBackward[1]
    }
    return longestCommonPrefixForward[removed - 1]
}
