package spellcheck;

public class SpellChecker {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        if (args.length < 2) {
            throw new IllegalArgumentException("Too few arguments. Please take a look at the \"Running the Program\" " +
                    "section in the README.");
        }

        String dictionaryFilePath = args[0];
        String inputFilePath = args[1];

        DictionaryService dictionaryService = new DictionaryService(dictionaryFilePath);

        SpellCheckService spellcheckService = new SpellCheckService(dictionaryService, inputFilePath);

        spellcheckService.getMisspelledWords().forEach(misspelledWord -> {
            System.out.println(misspelledWord);
            System.out.println();
        });

        long endTime = System.currentTimeMillis();

        System.out.println("This process took " + (endTime - startTime) + " milliseconds.");
    }
}