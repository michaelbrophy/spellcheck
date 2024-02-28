package spellcheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        String dictionaryFilePath = args[0];
        String inputFilePath = args[1];

        DictionaryService dictionaryService = new DictionaryService(dictionaryFilePath);

        // Get all Misspelled words from in writefile.txt
        List<String> allInputFileWords = FileUtility.parseFileToWords(inputFilePath);

        List<String> allMisspelledWords = allInputFileWords
                .stream()
                .filter(word -> !dictionaryService.isWordValid(word))
                .toList();

        System.out.println(String.join(", ", allMisspelledWords));
    }
}