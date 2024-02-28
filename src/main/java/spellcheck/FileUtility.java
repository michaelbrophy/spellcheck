package spellcheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FileUtility {

    public static List<String> parseFileToWords(String filePath) {

        List<String> words = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String rawLine = scanner.nextLine();
                // TODO: trim all punctuation from rawLine

                String[] rawLineWords = rawLine.split(" ");

                Collections.addAll(words, rawLineWords);
            }

        } catch (FileNotFoundException fnfe) {
            System.err.println("File not found with path: " + filePath);
        }

        return words;
    }

    // TODO: implement this method
    private String removePunctuation(String input) {
        return "";
    }
}
