package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.config.TestConfig;
import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.incremental.HistoryManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SimpleTest {

    private static final PandaParser.Builder parserBuilder = PandaParser.builder();

    @BeforeAll
    static void init() {
        try {
            HistoryManager.setHistoryManagerInstance("", true);
        } catch (Exception ignored) {}
    }

    private void testParse(String fileName) {
        parserBuilder.setInput(fileName);
        parserBuilder.setOutput(TestConfig.OUTPUT_TEST_DIR);
        PandaParser p = parserBuilder.build();
        p.parse();
    }

    /*Simple parse Empty MD with DEFAULT template */
    @Test
    void testParseEmptyMd() {
        testParse(TestConfig.EMPTY_MD_TEST);
    }

    /*Simple parse Simple MD with DEFAULT template */
    @Test
    void testParseSimpleMd() {
        testParse(TestConfig.BASIC_MD_TEST);
    }

    /*Simple parse Lorem Ipsum MD with DEFAULT template */
    @Test
    void testParseLoremIpsumMd() {
        testParse(TestConfig.LOREM_IPSUM_MD_TEST);
    }

    @Test
    void testParseNotExistingFile_thenException() {
        Assertions.assertThrows(NullPointerException.class, () -> testParse(TestConfig.NOT_EXISTING_FILE));
    }

    @Test
    void testParseUsingNotExistingTemplate() {
        testParse(TestConfig.NOT_EXISTING_TEMPLATE);
    }

    @Test
    void testParseUsingListLandingTemplate() {
        testParse(TestConfig.BAKERY_MD_TEST);
    }

    @Test
    void testParseUsingDictionaryTemplate() {
        testParse(TestConfig.DICTIONARY_MD_TEST);
    }

    @Test
    void testParseUsingArrayTemplate() {
        testParse(TestConfig.ARRAY_MD_TEST);
    }
}