import org.junit.Test;
import spellcheck.InputFileService;
import spellcheck.InputFileWord;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InputFileServiceTest {

    @Test
    public void testParseInputFileToInputFileWords() {

        List<InputFileWord> inputFileWords = new ArrayList<>();

        // This is a bit messy, and could be cleaned up by pulling this data in through a file, but given the amount of
        // time worked on it, I thought this would be a fine enough and readable solution
        inputFileWords.add(new InputFileWord("Once", 1, 1));
        inputFileWords.add(new InputFileWord("upon", 1, 6));
        inputFileWords.add(new InputFileWord("a", 1, 11));
        inputFileWords.add(new InputFileWord("time", 1, 13));
        inputFileWords.add(new InputFileWord("in", 1, 19));
        inputFileWords.add(new InputFileWord("a", 1, 22));
        inputFileWords.add(new InputFileWord("quaint", 1, 24));
        inputFileWords.add(new InputFileWord("littel", 1, 31));
        inputFileWords.add(new InputFileWord("village", 1, 38));
        inputFileWords.add(new InputFileWord("there", 3, 1));
        inputFileWords.add(new InputFileWord("lived", 3, 7));
        inputFileWords.add(new InputFileWord("a", 3, 13));
        inputFileWords.add(new InputFileWord("young", 3, 15));
        inputFileWords.add(new InputFileWord("boy", 3, 21));
        inputFileWords.add(new InputFileWord("named", 3, 25));
        inputFileWords.add(new InputFileWord("Johnny", 3, 31));

        String filePath = "src/test/resources/testParseInputFileToInputFileWordsTest.txt";

        assertEquals(inputFileWords, InputFileService.parseInputFile(filePath));
    }
}
