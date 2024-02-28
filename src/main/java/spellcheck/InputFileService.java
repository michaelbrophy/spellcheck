package spellcheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputFileService {

    private final String filePath;
    private final List<InputFileWord> allInputFileWords;

    public InputFileService(String filePath) {
        this.filePath = filePath;

        this.allInputFileWords = parseInputFileToInputFileWords(filePath);
    }

    public List<InputFileWord> parseInputFileToInputFileWords(String filePath) {

        List<InputFileWord> inputFileWords = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            int lineCount = 1;

            while (scanner.hasNextLine()) {
                String rawLine = scanner.nextLine();

                int wordColumn = 1;

                for (String word : rawLine.split(" ")) {

                    if (word.isEmpty() || word.isBlank()) {
                        continue;
                    }

                    String formattedWord = removePunctuationAndWhitespace(word);

                    InputFileWord inputFileWord = new InputFileWord(formattedWord, lineCount, wordColumn);
                    inputFileWords.add(inputFileWord);

                    wordColumn++;
                }

                lineCount++;
            }

        } catch (FileNotFoundException fnfe) {
            System.err.println("File not found with path: " + filePath);
        }

        return inputFileWords;
    }

    private String removePunctuationAndWhitespace(String input) {
        return input.replaceAll("[\\p{P}\\s]+", "");
    }

    public List<InputFileWord> getAllInputFileWords() {
        return this.allInputFileWords;
    }
}
