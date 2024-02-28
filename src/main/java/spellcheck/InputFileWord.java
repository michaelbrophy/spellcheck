package spellcheck;

import java.util.HashSet;
import java.util.Set;

public class InputFileWord {

    private final String word;
    private Set<String> suggestedWords;
    private final int line;
    private final int column;

    public InputFileWord(String word, int line, int column) {
        this.word = word;
        this.line = line;
        this.column = column;

        this.suggestedWords = new HashSet<>();
    }

    public String toString() {
        return this.word + " (line: " + line + ", column: " + column + ") -> " + String.join(", ", this.suggestedWords);
    }

    public String getWord() {
        return this.word;
    }

    public void setSuggestedWords(Set<String> suggestedWords) {
        this.suggestedWords = suggestedWords;
    }
}
