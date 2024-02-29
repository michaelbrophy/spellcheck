package spellcheck;

public record WordSuggestion (String word, int levenshteinDistance){}
