package spellcheck;

public class SpellChecker {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        String dictionaryFilePath = args[0];
        String inputFilePath = args[1];

        DictionaryService dictionaryService = new DictionaryService(dictionaryFilePath);

        InputFileService inputFileService = new InputFileService(inputFilePath);

        SpellCheckService spellcheckService = new SpellCheckService(dictionaryService, inputFileService);

        spellcheckService.getAllMisspelledWords()
                .forEach(System.out::println);

        long endTime = System.currentTimeMillis();

        System.out.println("This process took " + (endTime - startTime) + " milliseconds.");
    }
}