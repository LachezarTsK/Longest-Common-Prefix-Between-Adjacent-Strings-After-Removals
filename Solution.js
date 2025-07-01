
/**
 * @param {string[]} words
 * @return {number[]}
 */
var longestCommonPrefix = function (words) {
    if (words.length < Util.MIN_WORDS_TO_HAVE_ONE_PAIR_AFTER_REMOVAL) {
        return new Array(words.length).fill(0);
    }

    const commonPrefix = new Array(words.length - 1);
    for (let i = 0; i < words.length - 1; ++i) {
        commonPrefix[i] = findLengthCommonPrefix(words[i], words[i + 1]);
    }

    const util = new Util(commonPrefix, words.length);
    const longestCommonPrefix = new Array(words.length);
    for (let i = 0; i < words.length; ++i) {
        longestCommonPrefix[i] = findLongestCommonPrefixForGivenWordsOrder(words, i, util);
    }

    return longestCommonPrefix;
};

/**
 * @param {string} firstWord
 * @param {string} secondWord
 * @return {number}
 */
function findLengthCommonPrefix(firstWord, secondWord) {
    const length = Math.min(firstWord.length, secondWord.length);
    let commonPrefixLength = 0;
    for (let i = 0; i < length; ++i) {
        if (firstWord.charAt(i) !== secondWord.charAt(i)) {
            break;
        }
        ++commonPrefixLength;
    }
    return commonPrefixLength;
}

/**
 * @param {string[]} words
 * @param {number} removed
 * @param {Util} util 
 * @return {number}
 */
function findLongestCommonPrefixForGivenWordsOrder(words, removed, util) {
    if (removed > 0 && removed < words.length - 1) {
        const commonPrefixAdjacentToRemoved = findLengthCommonPrefix(words[removed - 1], words[removed + 1]);
        return Math.max(commonPrefixAdjacentToRemoved,
                Math.max(util.longestCommonPrefixForward[removed - 1], util.longestCommonPrefixBackward[removed + 1]));
    }
    if (removed === 0) {
        return util.longestCommonPrefixBackward[1];
    }
    return util.longestCommonPrefixForward[removed - 1];
}

class Util {

    static MIN_WORDS_TO_HAVE_ONE_PAIR_AFTER_REMOVAL = 3;

    /**
     * @param {number[]} commonPrefix
     * @param {number} inputLength
     */
    constructor(commonPrefix, inputLength) {
        this.longestCommonPrefixForward = new Array(inputLength).fill(0);
        for (let i = 1; i < inputLength; ++i) {
            this.longestCommonPrefixForward[i] = Math.max(this.longestCommonPrefixForward[i - 1], commonPrefix[i - 1]);
        }

        this.longestCommonPrefixBackward = new Array(inputLength).fill(0);
        for (let i = inputLength - 2; i >= 0; --i) {
            this.longestCommonPrefixBackward[i] = Math.max(this.longestCommonPrefixBackward[i + 1], commonPrefix[i]);
        }
    }
}
