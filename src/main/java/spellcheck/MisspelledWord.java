package spellcheck;

import java.util.Set;

public class MisspelledWord {

    private final InputFileWord inputFileWord;
    private final Set<String> wordSuggestions;

    private final String surroundingContext;

    public MisspelledWord(InputFileWord inputFileWord, Set<String> wordSuggestions, String surroundingContext) {
        this.inputFileWord = inputFileWord;
        this.wordSuggestions = wordSuggestions;
        this.surroundingContext = surroundingContext;
    }

    public String toString() {
        return inputFileWord.word() +
                " (line: " + inputFileWord.line() + ", column: " + inputFileWord.column() + ") -> " +
                String.join(", ", wordSuggestions);
    }
}
