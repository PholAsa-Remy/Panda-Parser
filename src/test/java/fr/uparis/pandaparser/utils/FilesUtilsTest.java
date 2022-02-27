package fr.uparis.pandaparser.utils;

import lombok.extern.java.Log;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

/**
 * Class to Test FileUtilsMethods
 */
@Log
class FilesUtilsTest {

    private static final String NEW_FILE_PATH = "new-file-to-test";
    private static final String EXISTING_FILE_PATH = "existing-file-to-test";
    private static final String TEXT = "hello panda parser";

    @BeforeAll
    static void setUp() throws IOException {
        FilesUtils.createFileFromContent(EXISTING_FILE_PATH, TEXT);
    }

    /* *********************************** *
     *   TEST getContentFromPath methods   *
     * *********************************** */

    @Test
    void whenReadExistingFileUsingGetContentFromPath_thenExcept() throws IOException {
        assertEquals(TEXT, FilesUtils.getFileCotent(EXISTING_FILE_PATH));
    }

    @Test
    void whenReadNotExistingFileUsingGetContentFromPath_thenExcept() {
        assertThrows(IOException.class, () -> FilesUtils.getFileCotent(NEW_FILE_PATH));
    }

    /* *********************************** *
     *   TEST createFileFromContent methods   *
     * *********************************** */

    @Test
    void whenCreatingFileUsingCreateFileFromContent_thenCorrect() throws IOException {
        FilesUtils.createFileFromContent(NEW_FILE_PATH, TEXT);
    }

    @Test
    void whenCreatingFileUsingCreateFileFromContent_WithNullPathFile_thenExcept() {
        assertThrows(NullPointerException.class, () -> FilesUtils.createFileFromContent(null, TEXT));
    }

    @Test
    void whenCreatingFileUsingCreateFileFromContent_WithNullContent_thenExcept() {
        assertThrows(NullPointerException.class, () -> FilesUtils.createFileFromContent(NEW_FILE_PATH, null));
    }

    @AfterAll
    static void cleanAll() {
        File file = new File(NEW_FILE_PATH);
        log.info(NEW_FILE_PATH + " deleted : " + file.delete());
        log.info(EXISTING_FILE_PATH + " deleted : " + file.delete());
    }
}