package spellcheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DictionaryService {

    private final String filePath;
    private final Set<String> allValidWords;

    public DictionaryService(String filePath) {
        this.filePath = filePath;

        this.allValidWords = generateAllValidWordsFromFile(filePath);
    }

    // Parse a dictionary file containing one word per line
    private Set<String> generateAllValidWordsFromFile(String filePath) {

        Set<String> words = new HashSet<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                words.add(word);
            }

        } catch (FileNotFoundException fnfe) {
            System.err.println("File not found with path: " + filePath);
        }

        return words;
    }

    public boolean isWordValid(String word) {
        return allValidWords.contains(word);
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Set<String> getAllValidWords() {
        return this.allValidWords;
    }
}
