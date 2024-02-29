package spellcheck;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class InputFileService {

    private final String filePath;
    private final List<InputFileWord> allInputFileWords;

    public InputFileService(String filePath) {
        this.filePath = filePath;

        this.allInputFileWords = parseInputFileToInputFileWords();
    }

    public List<InputFileWord> parseInputFileToInputFileWords() {

        List<InputFileWord> inputFileWords = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            int lineCount = 1;

            while (scanner.hasNextLine()) {
                String rawLine = scanner.nextLine();

                inputFileWords.addAll(parseLineToInputFileWords(rawLine, lineCount));

                lineCount++;
            }

        } catch (FileNotFoundException fnfe) {
            System.err.println("File not found with path: " + filePath);
        }

        return inputFileWords;
    }

    private List<InputFileWord> parseLineToInputFileWords(String line, int lineNumber) {

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

                // Adding 1 to offset strings being 0-indexed
                inputFileWords.add(new InputFileWord(trimmedWord, lineNumber, i - currentWordLength + 1));

                currentWord = new StringBuilder();
            }
        }

        // Slightly modified code from the block above to handle the last word in a line
        if (!(currentWord.isEmpty() && currentWord.toString().isBlank())) {

            int currentWordLength = currentWord.toString().length();

            String trimmedWord = removePunctuationAndWhitespace(currentWord.toString());

            // Adding 1 to offset strings being 0-indexed
            inputFileWords.add(new InputFileWord(trimmedWord, lineNumber, line.length() - currentWordLength + 1));
        }

        //System.out.println("Line " + lineNumber + ": " + String.join(" ", inputFileWords.stream().map(InputFileWord::word).toArray(String[]::new)));

        return inputFileWords;
    }

    private String removePunctuationAndWhitespace(String input) {
        return input.replaceAll("[\\p{P}\\s]+", "");
    }

    public List<InputFileWord> getAllInputFileWords() {
        return Collections.unmodifiableList(allInputFileWords);
    }
}
