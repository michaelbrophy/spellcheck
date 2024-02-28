import org.junit.Test;
import spellcheck.DictionaryService;
import spellcheck.InputFileService;
import spellcheck.SpellcheckService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class SpellcheckServiceTest {

    @Test
    public void testCalculateLevenshteinDistance() {

        DictionaryService mockDictionaryService = mock(DictionaryService.class);
        InputFileService mockInputFileService = mock(InputFileService.class);

        SpellcheckService spellcheckService = new SpellcheckService(mockDictionaryService, mockInputFileService);

        int result1 = spellcheckService.calculateLevenshteinDistance("sitting", "kitten");
        assertEquals(3, result1);

        int result2 = spellcheckService.calculateLevenshteinDistance("Saturday", "Sunday");
        assertEquals(3, result2);

        int result3 = spellcheckService.calculateLevenshteinDistance("", "kitten");
        assertEquals(6, result3);

        int result4 = spellcheckService.calculateLevenshteinDistance("kitten", "");
        assertEquals(6, result4);
    }
}
