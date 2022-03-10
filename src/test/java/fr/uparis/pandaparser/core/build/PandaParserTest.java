package fr.uparis.pandaparser.core.build;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

import java.io.File;

import static fr.uparis.pandaparser.config.TestConfig.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Panda Parser Tests
 *
 * @author panda-parser group
 * @version 1.0.0
 * @since Mars 2022
 */
@Log
class PandaParserTest {


    /* *********************************
     *      Panda parser Builder Tests    *
     * *********************************/

    @Test
    void whenCreatePandaParserWithAllArgsBuilder_thenCorrect() {
        assertNotNull(PandaParser.builder().setInput(INPUT_TEST_DIR).setOutput(OUTPUT_TEST_DIR).setJobs(4).isWatched(true).build());
    }


    @Test
    void whenCreatePandaParserWithOutputWithSeparator_thenCorrect() {
        assertEquals((OUTPUT_TEST_DIR + File.separator),
                PandaParser.builder()
                        .setOutput(OUTPUT_TEST_DIR + File.separator)
                        .build()
                        .getOutput());
    }

    @Test
    void whenCreatePandaParserWithOutputWithoutSeparator_thenSeparator() {
        assertEquals((OUTPUT_TEST_DIR + File.separator),
                PandaParser.builder()
                        .setOutput(OUTPUT_TEST_DIR)
                        .build()
                        .getOutput());
    }

    @Test
    void whenCreatePandaParserWithInputWithoutSeparator_thenSeparator() {
        assertEquals((INPUT_TEST_DIR + File.separator),
                PandaParser.builder()
                        .setInput(INPUT_TEST_DIR)
                        .build()
                        .getInput());
    }

    @Test
    void whenCreatePandaParserWithInputWithoutSeparator_thenCorrect() {
        assertEquals((INPUT_TEST_DIR + File.separator),
                PandaParser.builder()
                        .setInput(INPUT_TEST_DIR + File.separator)
                        .build()
                        .getInput());
    }


    /* *********************************
     *     Panda parser type Tests     *
     * *********************************/
    @Test
    void whenTryToParseWithEmptyInput_thenTypeShouldBeSite() {
        PandaParser parser = PandaParser.builder().build();
        assertEquals(ParserType.SITE, parser.getType());
    }

    @Test
    void whenTryToParsWithMdFile_thenTypeShouldBeSimple() {
        PandaParser parser = PandaParser.builder().setInput(MD_FILE).build();
        assertEquals(ParserType.SIMPLE, parser.getType());
    }

    @Test
    void whenTryToParseWithDirectory_thenTypeShouldBeSite() {
        PandaParser parser = PandaParser.builder().setInput(INPUT_TEST_DIR).build();
        assertEquals(ParserType.SITE, parser.getType());
    }


}