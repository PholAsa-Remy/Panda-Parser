package fr.uparis.pandaparser.core.build.site;


import fr.uparis.pandaparser.core.build.PandaParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static fr.uparis.pandaparser.config.TestConfig.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SiteTest {

    private PandaParser pandaParser;


    @BeforeEach
    void setUp() {
        pandaParser = PandaParser.builder().setInput(INPUT_TEST_DIR).build();
    }

    @Test
    void whenParseValideDirectory_thenCorrect() {
        this.pandaParser.parse();
    }

    @Test
    void whenParseNotValideDirectory_thenErrorMessage() {
        PandaParser.builder().setInput("").build().parse();
    }

    @Test
    void checkStaticFilesWhenBuildingSite(){
        PandaParser testStatic = PandaParser.builder().setInput(INPUT_TEST_DIR).setOutput(OUTPUT_TEST_DIR).build();
        testStatic.parse();
        File file1 = new File (OUTPUT_TEST_DIR + TEST_JPG_PATH);
        File file2 = new File (OUTPUT_TEST_DIR + TEST_CSS_PATH);
        File file3 = new File (OUTPUT_TEST_DIR + TEST_MP3_PATH);
        assertTrue (file1.exists());
        assertTrue (file2.exists());
        assertTrue  (file3.exists());
    }

    @Test
    void checkNoStaticFilesIsNotTransferInTheStaticDirectory(){
        PandaParser testStatic = PandaParser.builder().setInput(INPUT_TEST_DIR).setOutput(OUTPUT_TEST_DIR).build();
        testStatic.parse();
        File file = new File (OUTPUT_TEST_DIR + TEST_MD_STATIC_PATH);
        assertFalse (file.exists());
    }

}