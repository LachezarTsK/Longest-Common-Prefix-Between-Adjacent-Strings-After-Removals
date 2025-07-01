
#include <span>
#include <string>
#include <vector>
#include <algorithm>
#include <string_view>
using namespace std;

class Solution {

    static const int MIN_WORDS_TO_HAVE_ONE_PAIR_AFTER_REMOVAL = 3;

    vector<int> longestCommonPrefixForward;
    vector<int> longestCommonPrefixBackward;

public:
    vector<int> longestCommonPrefix(const vector<string>& words) {
        if (words.size() < MIN_WORDS_TO_HAVE_ONE_PAIR_AFTER_REMOVAL) {
            return vector<int>(words.size());
        }

        vector<int> commonPrefix(words.size() - 1);
        for (int i = 0; i < words.size() - 1; ++i) {
            commonPrefix[i] = findLengthCommonPrefix(words[i], words[i + 1]);
        }

        longestCommonPrefixForward.resize(words.size());
        for (int i = 1; i < words.size(); ++i) {
            longestCommonPrefixForward[i] = max(longestCommonPrefixForward[i - 1], commonPrefix[i - 1]);
        }

        longestCommonPrefixBackward.resize(words.size());
        for (int i = words.size() - 2; i >= 0; --i) {
            longestCommonPrefixBackward[i] = max(longestCommonPrefixBackward[i + 1], commonPrefix[i]);
        }

        vector<int> longestCommonPrefix(words.size());
        for (int i = 0; i < words.size(); ++i) {
            longestCommonPrefix[i] = findLongestCommonPrefixForGivenWordsOrder(words, i);
        }

        return longestCommonPrefix;
    }

private:
    int findLengthCommonPrefix(string_view firstWord, string_view secondWord) const {
        int length = min(firstWord.length(), secondWord.length());
        int commonPrefixLength = 0;
        for (int i = 0; i < length; ++i) {
            if (firstWord[i] != secondWord[i]) {
                break;
            }
            ++commonPrefixLength;
        }
        return commonPrefixLength;
    }

    int findLongestCommonPrefixForGivenWordsOrder(span<const string> words, int removed) {
        if (removed > 0 && removed < words.size() - 1) {
            int commonPrefixAdjacentToRemoved = findLengthCommonPrefix(words[removed - 1], words[removed + 1]);
            return max(commonPrefixAdjacentToRemoved,
                    max(longestCommonPrefixForward[removed - 1], longestCommonPrefixBackward[removed + 1]));
        }
        if (removed == 0) {
            return longestCommonPrefixBackward[1];
        }
        return longestCommonPrefixForward[removed - 1];
    }
};
