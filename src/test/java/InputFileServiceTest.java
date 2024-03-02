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

        /* This is a bit messy, and could be cleaned up by pulling this data in through a file, but given the amount of
         time worked on it, I thought this would be a fine enough and readable solution. It will also currently not work
         if the value of NUMBER_OF_CONTEXT_WORDS_ON_EITHER_SIDE is changed */
        inputFileWords.add(new InputFileWord("Once", "Once upon a", 1, 1));
        inputFileWords.add(new InputFileWord("upon", "Once upon a time,", 1, 6));
        inputFileWords.add(new InputFileWord("a", "Once upon a time, in", 1, 11));
        inputFileWords.add(new InputFileWord("time", "upon a time, in a", 1, 13));
        inputFileWords.add(new InputFileWord("in", "a time, in a quaint", 1, 19));
        inputFileWords.add(new InputFileWord("a", "time, in a quaint littel", 1, 22));
        inputFileWords.add(new InputFileWord("quaint", "in a quaint littel village,", 1, 24));
        inputFileWords.add(new InputFileWord("littel", "a quaint littel village,", 1, 31));
        inputFileWords.add(new InputFileWord("village", "quaint littel village,", 1, 38));
        inputFileWords.add(new InputFileWord("there", "there lived a", 3, 1));
        inputFileWords.add(new InputFileWord("lived", "there lived a young", 3, 7));
        inputFileWords.add(new InputFileWord("a", "there lived a young boy", 3, 13));
        inputFileWords.add(new InputFileWord("young", "lived a young boy named", 3, 15));
        inputFileWords.add(new InputFileWord("boy", "a young boy named Johnny.", 3, 21));
        inputFileWords.add(new InputFileWord("named", "young boy named Johnny.", 3, 25));
        inputFileWords.add(new InputFileWord("Johnny", "boy named Johnny.", 3, 31));

        String filePath = "src/test/resources/testParseInputFileToInputFileWordsTest.txt";

        assertEquals(inputFileWords, InputFileService.parseInputFile(filePath));
    }
}
