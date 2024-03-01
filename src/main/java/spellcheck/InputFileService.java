package spellcheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputFileService {

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

        List<InputFileWord> inputFileWords = new ArrayList<>();

        StringBuilder currentWord = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char currentCharacter = line.charAt(i);

            if (!Character.isWhitespace(currentCharacter)) {
                currentWord.append(currentCharacter);
                continue;
            }

            if (!(currentWord.isEmpty() && currentWord.toString().isBlank())) {

                int currentWordLength = currentWord.toString().length();

                String trimmedWord = removePunctuationAndWhitespace(currentWord.toString());

                int endIndexOfWord = i + 1;
                int startIndexOfWord = endIndexOfWord - currentWordLength;

                String wordContext = getContextForWord(line, startIndexOfWord, endIndexOfWord);

                // Adding 1 to offset strings being 0-indexed
                inputFileWords.add(new InputFileWord(trimmedWord, wordContext, lineNumber, startIndexOfWord));

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

            String wordContext = getContextForWord(line, startIndexOfWord, endIndexOfWord);

            // Adding 1 to offset strings being 0-indexed
            inputFileWords.add(new InputFileWord(trimmedWord, wordContext, lineNumber, startIndexOfWord));
        }

        return inputFileWords;
    }

    private static String getContextForWord(String line, int startIndexOfWord, int endIndexOfWord) {

        // TODO: implement this

        return "";
    }

    private static String removePunctuationAndWhitespace(String input) {
        return input.replaceAll("[\\p{P}\\s]+", "");
    }
}
