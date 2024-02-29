package spellcheck;

import java.util.*;

public class SpellCheckService {

    // Change this to adjust the flexibility of the word suggestion list
    private final int WORD_SUGGESTION_DISTANCE_LIMIT = 2;

    // Change this to adjust the limit of suggested words appearing for each misspelled word
    private final int WORD_SUGGESTION_LIST_SIZE_LIMIT = 5;

    // Change this to finish searching for word suggestions as soon as the WORD_SUGGESTION_LIST_SIZE_LIMIT is reached
    private final boolean SHOULD_WORD_SUGGESTION_STOP_AT_SIZE_LIMIT = false;

    private final DictionaryService dictionaryService;
    private final InputFileService inputFileService;

    private final List<MisspelledWord> allMisspelledWords;

    public SpellCheckService(DictionaryService dictionaryService, InputFileService inputFileService) {
        this.dictionaryService = dictionaryService;
        this.inputFileService = inputFileService;

        this.allMisspelledWords = spellCheckAllInputFileWords(findAllMisspelledInputFileWords());
    }

    private List<InputFileWord> findAllMisspelledInputFileWords() {
        return inputFileService.getAllInputFileWords()
                .stream()
                .filter(this::isWordMisspelled)
                .toList();
    }

    private List<MisspelledWord> spellCheckAllInputFileWords(List<InputFileWord> inputFileWords) {
        List<MisspelledWord> misspelledWords = new ArrayList<>();

        for (InputFileWord inputFileWord : inputFileWords) {

            Set<String> wordSuggestions = getWordSuggestions(inputFileWord.word());
            misspelledWords.add(new MisspelledWord(inputFileWord, wordSuggestions));
        }

        return misspelledWords;
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

        PriorityQueue<WordSuggestion> wordSuggestions = new PriorityQueue<>(Comparator.comparingInt(WordSuggestion::levenshteinDistance));

        int foundWordSuggestionCount = 0;

        for (String dictionaryWord : dictionaryService.getAllValidWords()) {

            int lengthDifferenceBetweenWords = Math.abs(misspelledWord.length() - dictionaryWord.length());

            // Skipping words that are too different in length to be potential suggestions
            if (lengthDifferenceBetweenWords > WORD_SUGGESTION_DISTANCE_LIMIT) {
                continue;
            }

            int levenshteinDistance = calculateLevenshteinDistance(misspelledWord, dictionaryWord);

            if (levenshteinDistance <= WORD_SUGGESTION_DISTANCE_LIMIT) {
                wordSuggestions.add(new WordSuggestion(dictionaryWord, levenshteinDistance));
                foundWordSuggestionCount++;
            }

            if (SHOULD_WORD_SUGGESTION_STOP_AT_SIZE_LIMIT &&
                    foundWordSuggestionCount >= WORD_SUGGESTION_LIST_SIZE_LIMIT) {
                break;
            }
        }

        return trimPriorityQueueToWordSuggestionLimitAndConvertToSet(wordSuggestions);
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

    private Set<String> trimPriorityQueueToWordSuggestionLimitAndConvertToSet(PriorityQueue<WordSuggestion> wordSuggestions) {

        Set<String> trimmedWordSuggestionsSet = new HashSet<>();

        int count = 0;

        for (WordSuggestion wordSuggestion : wordSuggestions) {
            trimmedWordSuggestionsSet.add(wordSuggestion.word());
            count++;

            if (count >= WORD_SUGGESTION_LIST_SIZE_LIMIT) {
                break;
            }
        }

        return trimmedWordSuggestionsSet;
    }

    public List<MisspelledWord> getAllMisspelledWords() {
        return allMisspelledWords;
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
}
