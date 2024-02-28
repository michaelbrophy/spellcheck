package spellcheck;

import java.util.List;

public class DictionaryService {

    private final String filePath;
    private final List<String> allValidWords;

    // TODO: reorder dictionary into CBT for performance gain
    // private Node allValidWordsRoot

    public DictionaryService(String filePath) {
        this.filePath = filePath;

        this.allValidWords = getAllParsedWordsFromFile();
    }

    public List<String> getAllParsedWordsFromFile() {
        return FileUtility.parseFileToWords(this.filePath);
    }

    public boolean isWordValid(String word) {
        return allValidWords.contains(word);
    }

    public String getFilePath() {
        return this.filePath;
    }

    public List<String> getAllValidWords() {
        return this.allValidWords;
    }
}
