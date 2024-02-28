package spellcheck;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        String dictionaryFilePath = args[0];
        String inputFilePath = args[1];

        DictionaryService dictionaryService = new DictionaryService(dictionaryFilePath);

        InputFileService inputFileService = new InputFileService(inputFilePath);

        SpellcheckService spellcheckService = new SpellcheckService(dictionaryService, inputFileService);

        for (InputFileWord inputFileWord : spellcheckService.getAllMisspelledWords()) {
            inputFileWord.setSuggestedWords(spellcheckService.getSuggestedWords(inputFileWord));
        }

        long endTime = System.currentTimeMillis();

        for (InputFileWord inputFileWord : spellcheckService.getAllMisspelledWords()) {

            System.out.println(inputFileWord.toString());
        }

        System.out.println("This process took " + (endTime - startTime) + " milliseconds.");
    }
}