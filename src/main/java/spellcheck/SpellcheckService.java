package spellcheck;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpellcheckService {

    // Change this to adjust the flexibility of the word suggestion list
    private final int WORD_SUGGESTION_DISTANCE_LIMIT = 2;

    private final DictionaryService dictionaryService;
    private final InputFileService inputFileService;

    private final List<InputFileWord> allMisspelledWords;

    public SpellcheckService(DictionaryService dictionaryService, InputFileService inputFileService) {
        this.dictionaryService = dictionaryService;
        this.inputFileService = inputFileService;

        this.allMisspelledWords = generateAllMisspelledWords();
    }

    private List<InputFileWord> generateAllMisspelledWords() {
        return inputFileService.getAllInputFileWords()
                .stream()
                .filter(inputFileWord -> !dictionaryService.isWordValid(inputFileWord.getWord()))
                .toList();
    }

    public Set<String> getSuggestedWords(InputFileWord inputFileWord) {

        String misspelledWord = inputFileWord.getWord();

        Set<String> suggestedWords = new HashSet<>();

        for (String dictionaryWord : dictionaryService.getAllValidWords()) {

            int lengthDifferenceBetweenWords = Math.abs(misspelledWord.length() - dictionaryWord.length());

            if (lengthDifferenceBetweenWords > WORD_SUGGESTION_DISTANCE_LIMIT) {
                continue;
            }

            if (calculateLevenshteinDistance(misspelledWord, dictionaryWord) <= WORD_SUGGESTION_DISTANCE_LIMIT) {
                suggestedWords.add(dictionaryWord);
            }
        }

        return suggestedWords;
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

        for (int[] row : levenshteinDistance) {
            Arrays.fill(row, 0);
        }

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

    public List<InputFileWord> getAllMisspelledWords() {
        return this.allMisspelledWords;
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
