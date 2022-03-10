package fr.uparis.pandaparser.utils;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.config.Extension;
import lombok.extern.java.Log;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.imageio.stream.FileImageInputStream;

import static fr.uparis.pandaparser.config.TestConfig.INPUT_TEST_DIR;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;

/**
 * Class to Test FileUtilsMethods
 */
@Log
class FilesUtilsTest {

    private static final String DIR_FOR_TESTING = "FilesUtilsTest-dir" + File.separator;
    private static final String BUILD_DIR = "build";
    private static final String NEW_DIR_PATH = "new-dir-to-test";

    private static final String NEW_MD_FILE_NAME = "new-file-to-test.md";
    private static final String NEW_HTML_FILE_NAME = "new-file-to-test.html";
    private static final String NEW_FILE_PATH = DIR_FOR_TESTING + NEW_MD_FILE_NAME;
    private static final String EXISTING_FILE_PATH = DIR_FOR_TESTING + "existing-file-to-test.md";
    private static final String TEXT = DIR_FOR_TESTING + "# hello panda parser";
    private static final String NO_ACCESS_DIR = "src/test/resources/read-only-dir";
    private static final String NO_ACCESS_DIR_INPUT = "src/test/resources/read-only-dir/input";


    @BeforeAll
    static void setUp() throws IOException {
        Files.createDirectory(Path.of(DIR_FOR_TESTING));
        FilesUtils.createFileFromContent(EXISTING_FILE_PATH, TEXT);

        File readOnlyDir = new File(NO_ACCESS_DIR);
        readOnlyDir.mkdirs();
        readOnlyDir.setReadOnly();

    }

    /* *********************************** *
     *   TEST getContentFromPath method    *
     * *********************************** */

    @Test
    void whenReadExistingFileUsingGetContentFromPath_thenCorrect() throws IOException {
        assertEquals(TEXT, FilesUtils.getFileCotent(EXISTING_FILE_PATH));
    }

    @Test
    void whenReadNotExistingFileUsingGetContentFromPath_thenExcept() {
        assertThrows(IOException.class, () -> FilesUtils.getFileCotent(NEW_FILE_PATH));
    }

    @Test
    void whenReadNullFileUsingGetContentFromPath_thenExcept() {
        assertThrows(NullPointerException.class, () -> FilesUtils.getFileCotent(null));
    }

    /* ************************************** *
     *   TEST createFileFromContent method    *
     * ************************************** */

    @Test
    void whenCreatingFileUsingCreateFileFromContent_thenCorrect() throws IOException {
        FilesUtils.createFileFromContent(NEW_FILE_PATH, TEXT);
    }

    @Test
    void whenCreatingFileUsingCreateFileFromContentInExistingDir_thenCorrect() throws IOException {
        FilesUtils.createFileFromContent(INPUT_TEST_DIR + NEW_FILE_PATH, TEXT);
    }

    @Test
    void whenCreatingFileUsingCreateFileFromContent_WithNullPathFile_thenExcept() {
        assertThrows(NullPointerException.class, () -> FilesUtils.createFileFromContent(null, TEXT));
    }

    @Test
    void whenCreatingFileUsingCreateFileFromContent_WithNullContent_thenExcept() {
        assertThrows(NullPointerException.class, () -> FilesUtils.createFileFromContent(NEW_FILE_PATH, null));
    }

    // local only
//    @Test
//    void whenCreatingFileUsingCreateFileFromContent_WithRootDirectory_thenExcept() {
//        assertThrows(RuntimeException.class, () -> FilesUtils.createFileFromContent(NO_ACCESS_DIR_INPUT + File.separator + NEW_MD_FILE_NAME, TEXT));
//    }

    /* ***************************************** *
     *   TEST getAllFilesFromDirectory method    *
     * ***************************************** */

    @Test
    void whenListingFilesUsingGetAllFilesFromDirectory_withNullDir_thenExcept() {
        assertThrows(NullPointerException.class, () -> FilesUtils.getAllFilesFromDirectory(null));
    }

    @Test
    void whenListingFilesUsingGetAllFilesFromDirectory_withNotExistingDir_thenExcept() {
        assertThrows(NoSuchFileException.class, () -> FilesUtils.getAllFilesFromDirectory(NEW_DIR_PATH));
    }

    @Test
    void whenListingFilesUsingGetAllFilesFromDirectory_withSimpleFile_thenExcept() {
        assertThrows(NotDirectoryException.class, () -> FilesUtils.getAllFilesFromDirectory(EXISTING_FILE_PATH));
    }

    @Test
    void whenListingFilesUsingGetAllFilesFromDirectory_withDir_thenEmpty() throws IOException {
        assertTrue(FilesUtils.getAllFilesFromDirectory(BUILD_DIR).isEmpty());
    }

    @Test
    void whenListingFilesUsingGetAllFilesFromDirectory_withExistingDir_thenCorrect() throws IOException {
        FilesUtils.getAllFilesFromDirectory(DIR_FOR_TESTING);
    }

    @Test
    void whenListingFilesUsingGetAllFilesFromDirectory_withExistingDir_withOneFile_thenCorrect() throws IOException {
        assertEquals(1, FilesUtils.getAllFilesFromDirectory(DIR_FOR_TESTING).size());
    }

    @Test
    void whenListingFilesUsingGetAllFilesFromDirectory_withExistingDirAndCheckContent_thenCorrect() throws IOException {
        assertTrue(FilesUtils.getAllFilesFromDirectory(DIR_FOR_TESTING).contains(EXISTING_FILE_PATH));
    }

    @Test
    void whenListingSpecificFilesUsingGetAllFilesFromDirectory_thenCorrect() throws IOException {
        assertTrue(FilesUtils.getAllFilesFromDirectory(DIR_FOR_TESTING, Extension.MD).contains(EXISTING_FILE_PATH));
    }

    @Test
    void whenListingSpecificNoFilesUsingGetAllFilesFromDirectory_thenEmpty_thenCorrect() throws IOException {
        assertTrue(FilesUtils.getAllFilesFromDirectory(DIR_FOR_TESTING, Extension.TOML).isEmpty());
    }

    @Test
    void whenListingSpecificNullFilesUsingGetAllFilesFromDirectory_thenExcept() {
        assertThrows(NullPointerException.class, () -> FilesUtils.getAllFilesFromDirectory(DIR_FOR_TESTING, null));
    }

    @Test
    void whenListingSpecificFilesUsingGetAllFilesFromDirectory_withNullDirectory_thenExcept() {
        assertThrows(NullPointerException.class, () -> FilesUtils.getAllFilesFromDirectory(null, Extension.MD));
    }

    @Test
    void whenListingSpecificFilesUsingGetAllFilesFromDirectory_withNoDirectory_thenExcept() {
        assertThrows(NoSuchFileException.class, () -> FilesUtils.getAllFilesFromDirectory(NEW_DIR_PATH, Extension.MD));
    }

    /* **************************** *
     *   TEST getFileName method    *
     * **************************** */
    @Test
    void whenGetFileNameUsingGetAllFilesName_thenCorrect() {
        assertEquals(NEW_MD_FILE_NAME, FilesUtils.getFileName(NEW_FILE_PATH));
    }

    @Test
    void whenGetFileNameUsingGetAllFilesName_withNullPath_thenExcept() {
        assertThrows(NullPointerException.class, () -> FilesUtils.getFileName(null));
    }

    /* ****************************************** *
     *   TEST getHtmlFilenameFromMdFile method    *
     * ****************************************** */

    @Test
    void whenGetHtmlFilenameFromNullMdFile_thenNullPointerExcept() {
        assertThrows(NullPointerException.class, () -> FilesUtils.getHtmlFilenameFromMdFile(null));
    }

    @Test
    void whenGetHtmlFilenameFromValideMdFile_thenCorrect() {
        assertEquals(NEW_HTML_FILE_NAME, FilesUtils.getHtmlFilenameFromMdFile(NEW_MD_FILE_NAME));
    }


    @AfterAll
    static void cleanAll() throws IOException {

        Files.deleteIfExists(Path.of(EXISTING_FILE_PATH));
        Files.deleteIfExists(Path.of(NEW_FILE_PATH));

        Files.deleteIfExists(Path.of(NEW_DIR_PATH));
        Files.deleteIfExists(Path.of(DIR_FOR_TESTING));

        Files.deleteIfExists(Path.of(NO_ACCESS_DIR));

    }
}