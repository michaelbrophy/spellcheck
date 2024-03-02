package spellcheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class InputFileService {

    // Change this to adjust the number of words appearing in the context of generated InputFileWords
    public static final int NUMBER_OF_CONTEXT_WORDS_ON_EITHER_SIDE = 2;

    public static List<InputFileWord> parseInputFile(String filePath) {

        List<InputFileWord> inputFileWords = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            int lineCount = 1;

            while (scanner.hasNextLine()) {
                String rawLine = scanner.nextLine();

                inputFileWords.addAll(parseLine(rawLine, lineCount));

                lineCount++;
            }

        } catch (FileNotFoundException fnfe) {
            System.err.println("File not found with path: " + filePath);
        }

        return inputFileWords;
    }

    private static List<InputFileWord> parseLine(String line, int lineNumber) {

        if (line.isBlank()) {
            return new ArrayList<>();
        }

        List<InputFileWord> inputFileWords = new ArrayList<>();

        // Using this as a helper for my simplistic approach to providing context given the time constraint
        List<String> rawWordsInLine = Arrays.stream(line.split(" "))
                .filter(Predicate.not(String::isBlank)).toList();

        int currentWordInLine = 0;
        StringBuilder currentWord = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char currentCharacter = line.charAt(i);

            if (!Character.isWhitespace(currentCharacter)) {
                currentWord.append(currentCharacter);
                continue;
            }

            if (!currentWord.toString().isBlank()) {

                int currentWordLength = currentWord.toString().length();

                String trimmedWord = removePunctuationAndWhitespace(currentWord.toString());

                int endIndexOfWord = i + 1;
                int startIndexOfWord = endIndexOfWord - currentWordLength;

                String wordContext = getContextForWord(rawWordsInLine, currentWordInLine);

                // Adding 1 to offset strings being 0-indexed
                inputFileWords.add(new InputFileWord(trimmedWord, wordContext, lineNumber, startIndexOfWord));

                currentWordInLine++;
                currentWord = new StringBuilder();
            }
        }

        // Slightly modified code from the block above to handle the last word in a line
        // Normally I would look to reduce duplication here, but keeping this under time pressure
        if (!(currentWord.isEmpty() && currentWord.toString().isBlank())) {

            int currentWordLength = currentWord.toString().length();

            String trimmedWord = removePunctuationAndWhitespace(currentWord.toString());

            int endIndexOfWord = line.length() + 1;
            int startIndexOfWord = endIndexOfWord - currentWordLength;

            String wordContext = getContextForWord(rawWordsInLine, currentWordInLine);

            // Adding 1 to offset strings being 0-indexed
            inputFileWords.add(new InputFileWord(trimmedWord, wordContext, lineNumber, startIndexOfWord));
        }

        return inputFileWords;
    }

    private static String getContextForWord(List<String> rawWordsInLine, int currentWordInLine) {

        int contextStartWordIndex = Math.max(0, currentWordInLine - NUMBER_OF_CONTEXT_WORDS_ON_EITHER_SIDE);
        int contextEndWordIndex =
                Math.min(rawWordsInLine.size(), currentWordInLine + NUMBER_OF_CONTEXT_WORDS_ON_EITHER_SIDE + 1);

        // Using an approach to providing context that eliminates extra whitespace given the time constraint
        return String.join(" ", rawWordsInLine.subList(contextStartWordIndex, contextEndWordIndex));
    }

    private static String removePunctuationAndWhitespace(String input) {
        return input.replaceAll("[\\p{P}\\s]+", "");
    }
}
