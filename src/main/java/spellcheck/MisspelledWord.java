package spellcheck;

import java.util.Set;

public class MisspelledWord {

    private final InputFileWord inputFileWord;
    private final Set<String> wordSuggestions;

    public MisspelledWord(InputFileWord inputFileWord, Set<String> wordSuggestions) {
        this.inputFileWord = inputFileWord;
        this.wordSuggestions = wordSuggestions;
    }

    public String toString() {
        return inputFileWord.word() +
                " (line: " + inputFileWord.line() + ", column: " + inputFileWord.column() + ") -> " +
                String.join(", ", wordSuggestions);
    }
}
