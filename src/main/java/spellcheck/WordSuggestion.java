package spellcheck;

public record WordSuggestion (String word, int levenshteinDistance) implements Comparable<WordSuggestion> {

    @Override
    public int compareTo(WordSuggestion other) {

        if (other == null) {
            return -1;
        }

        if (levenshteinDistance == other.levenshteinDistance) {

            return word.compareTo(other.word);
        } else {

            return Integer.compare(levenshteinDistance, other.levenshteinDistance);
        }
    }
}
