import org.junit.Test;
import org.mockito.MockedStatic;
import spellcheck.DictionaryService;
import spellcheck.InputFileService;
import spellcheck.SpellCheckService;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SpellCheckServiceTest {

    @Test
    public void testCalculateLevenshteinDistance() {

        try(MockedStatic<InputFileService> inputFileServiceMockedStatic = mockStatic(InputFileService.class)) {
            inputFileServiceMockedStatic.when(() ->
                    InputFileService.parseInputFile(any())).thenReturn(new ArrayList<>());

            DictionaryService mockDictionaryService = mock(DictionaryService.class);

            SpellCheckService spellcheckService = new SpellCheckService(mockDictionaryService, "");

            int result1 = spellcheckService.calculateLevenshteinDistance("sitting", "kitten");
            assertEquals(3, result1);

            int result2 = spellcheckService.calculateLevenshteinDistance("Saturday", "Sunday");
            assertEquals(3, result2);

            int result3 = spellcheckService.calculateLevenshteinDistance("", "kitten");
            assertEquals(6, result3);

            int result4 = spellcheckService.calculateLevenshteinDistance("kitten", "");
            assertEquals(6, result4);

            int result5 = spellcheckService.calculateLevenshteinDistance("siTTIng", "kIttEn");
            assertEquals(3, result5);
        }
    }
}
