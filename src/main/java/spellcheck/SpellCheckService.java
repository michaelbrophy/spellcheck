package spellcheck;

import java.util.*;
import java.util.stream.Collectors;

public class SpellCheckService {

    // Change this to adjust the flexibility of the word suggestion list
    public static final int WORD_SUGGESTION_DISTANCE_LIMIT = 2;

    // Change this to adjust the limit of suggested words appearing for each misspelled word
    public static final int WORD_SUGGESTION_LIST_SIZE_LIMIT = 5;

    // Change this to finish searching for word suggestions as soon as the WORD_SUGGESTION_LIST_SIZE_LIMIT is reached
    public static final boolean SHOULD_WORD_SUGGESTION_STOP_AT_LIST_SIZE_LIMIT = true;

    private final DictionaryService dictionaryService;

    private final List<MisspelledWord> misspelledWords;

    public SpellCheckService(DictionaryService dictionaryService, String filePath) {
        this.dictionaryService = dictionaryService;

        this.misspelledWords = spellCheckWords(findMisspelledWords(filePath));
    }

    private List<InputFileWord> findMisspelledWords(String filePath) {

        return InputFileService.parseInputFile(filePath)
                .stream()
                .filter(this::isWordMisspelled)
                .toList();
    }

    public List<MisspelledWord> spellCheckWords(List<InputFileWord> inputFileWords) {

        return inputFileWords
                .stream()
                .filter(this::isWordMisspelled)
                .map(misspelledWord -> new MisspelledWord(misspelledWord, getWordSuggestions(misspelledWord.word())))
                .toList();
    }

    // Overloading this particular method to display that this could be convenient to have in a larger codebase
    public boolean isWordMisspelled(InputFileWord inputFileWord) {
        return isWordMisspelled(inputFileWord.word());
    }

    // Returns false if the word is either in the dictionary or it is a proper noun (begins with a capital letter)
    public boolean isWordMisspelled(String word) {

        if (word.isEmpty()) {
            return false;
        }

        return !(Character.isUpperCase(word.charAt(0)) ||
                dictionaryService.isWordInDictionary(word));
    }

    // If I had more time, I would implement caching in this method for repeated misspelled words
    // as well as utilizing multithreading to speed this up, as it's what takes the longest to compute
    public Set<String> getWordSuggestions(String misspelledWord) {

        SortedSet<WordSuggestion> wordSuggestions = new TreeSet<>();

        for (String dictionaryWord : dictionaryService.getAllValidWords()) {

            int lengthDifferenceBetweenWords = Math.abs(misspelledWord.length() - dictionaryWord.length());

            // Skipping words that are too different in length to be potential suggestions
            if (lengthDifferenceBetweenWords > WORD_SUGGESTION_DISTANCE_LIMIT) {
                continue;
            }

            int levenshteinDistance = calculateLevenshteinDistance(misspelledWord, dictionaryWord);

            if (levenshteinDistance <= WORD_SUGGESTION_DISTANCE_LIMIT) {

                wordSuggestions.add(new WordSuggestion(dictionaryWord, levenshteinDistance));
            }

            if (SHOULD_WORD_SUGGESTION_STOP_AT_LIST_SIZE_LIMIT
                    && wordSuggestions.size() >= WORD_SUGGESTION_LIST_SIZE_LIMIT) {
                break;
            }
        }

        return trimWordSuggestionsToListSizeLimit(wordSuggestions)
                .stream()
                .map(WordSuggestion::word)
                .collect(Collectors.toSet());
    }

    private Set<WordSuggestion> trimWordSuggestionsToListSizeLimit(Set<WordSuggestion> wordSuggestions) {

        Set<WordSuggestion> trimmedWordSuggestions = new TreeSet<>();
        Iterator<WordSuggestion> wordSuggestionIterator = wordSuggestions.iterator();

        while (wordSuggestionIterator.hasNext() && trimmedWordSuggestions.size() < WORD_SUGGESTION_LIST_SIZE_LIMIT) {
            WordSuggestion wordSuggestion = wordSuggestionIterator.next();
            trimmedWordSuggestions.add(wordSuggestion);
        }

        return trimmedWordSuggestions;
    }

    // Using algorithm based off of
    // https://en.wikipedia.org/wiki/Levenshtein_distance#:~:text=in%20t.-,Iterative%20with%20full%20matrix,-%5Bedit%5D
    public int calculateLevenshteinDistance(String wordOne, String wordTwo) {

        int wordOneLength = wordOne.length();
        int wordTwoLength = wordTwo.length();

        if (wordOneLength == 0) {
            return wordTwoLength;
        }

        if (wordTwoLength == 0) {
            return wordOneLength;
        }

        String wordOneLowercase = wordOne.toLowerCase();
        String wordTwoLowercase = wordTwo.toLowerCase();

        int[][] levenshteinDistance = new int[wordOneLength + 1][wordTwoLength + 1];

        for (int i = 1; i <= wordOneLength; i++) {
            levenshteinDistance[i][0] = i;
        }

        for (int j = 1; j <= wordTwoLength; j++) {
            levenshteinDistance[0][j] = j;
        }

        for (int j = 1; j <= wordTwoLength; j++) {
            for (int i = 1; i <= wordOneLength; i++) {
                int substitutionValue = wordOneLowercase.charAt(i - 1) == wordTwoLowercase.charAt(j - 1) ? 0 : 1;

                levenshteinDistance[i][j] = minimum(
                        levenshteinDistance[i - 1][j] + 1,
                        levenshteinDistance[i][j - 1] + 1,
                        levenshteinDistance[i - 1][j - 1] + substitutionValue);
            }
        }

        return levenshteinDistance[wordOneLength][wordTwoLength];
    }

    private int minimum(int... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("At least one integer must be provided.");
        }

        int minimum = values[0];

        for (int i = 1; i < values.length; i++) {
            if (values[i] < minimum) {
                minimum = values[i];
            }
        }

        return minimum;
    }

    public List<MisspelledWord> getMisspelledWords() {
        return misspelledWords;
    }
}
