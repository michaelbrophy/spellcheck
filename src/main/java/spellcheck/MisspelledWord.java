package spellcheck;

import java.util.List;

public class MisspelledWord {

    private final String word;
    private final List<String> suggestedWords;

    public MisspelledWord(String word, List<String> suggestedWords) {
        this.word = word;
        this.suggestedWords = suggestedWords;
    }

    public String toString() {
        return this.word + " -> " + String.join(", ", this.suggestedWords);
    }

}
