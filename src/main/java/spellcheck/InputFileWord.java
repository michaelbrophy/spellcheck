package spellcheck;

public record InputFileWord (String word, String context, int line, int column) {}