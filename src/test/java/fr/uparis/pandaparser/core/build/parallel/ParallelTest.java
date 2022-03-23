package fr.uparis.pandaparser.core.build.parallel;

import org.junit.jupiter.api.Test;

import static fr.uparis.pandaparser.config.TestConfig.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParallelTest {

    @Test
    void createThreadParser_WithValidInputAndOutput_thenCorrect() {
        new ThreadParser(INPUT_TEST_DIR, OUTPUT_TEST_DIR);
    }

    @Test
    void createThreadParser_WithNullInput_thenNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ThreadParser(null, OUTPUT_TEST_DIR));
    }

    @Test
    void createThreadParser_WithNullOutput_thenNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ThreadParser(INPUT_TEST_DIR, null));
    }


    @Test
    void createThreadStaticFilesCopier_WithValidInputAndOutput_thenCorrect() {
        new ThreadStaticFilesCopier(INPUT_TEST_DIR, OUTPUT_TEST_DIR);
    }

    @Test
    void createThreadStaticFilesCopier_WithNullInput_thenNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ThreadStaticFilesCopier(null, OUTPUT_TEST_DIR));
    }

    @Test
    void createThreadFileStaticCopier_WithNullOutput_thenNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ThreadStaticFilesCopier(INPUT_TEST_DIR, null));
    }

    @Test
    void createListOfThreadFileStaticCopiers_WithNullInput_thenNullPointerException() {
        assertThrows(NullPointerException.class, () -> ThreadStaticFilesCopier.createListOfThreads(null, OUTPUT_TEST_DIR));
    }

    @Test
    void createListOfThreadFileStaticCopiers_WithNullOutput_thenNullPointerException() {
        assertThrows(NullPointerException.class, () -> ThreadStaticFilesCopier.createListOfThreads(INPUT_TEST_DIR, null));
    }

    @Test
    void createListOfThreadFileStaticCopiers_WithNotExistingInput_thenEmptyList() {
        assertTrue(ThreadStaticFilesCopier.createListOfThreads(NOT_EXISTING_DIR, OUTPUT_TEST_DIR).isEmpty());
    }
}