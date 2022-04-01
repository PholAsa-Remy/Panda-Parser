package fr.uparis.pandaparser.core.build.incremental;
import fr.uparis.pandaparser.exception.InstanceInitialisationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class HistoryManagerTest {

    private void init(String input, Boolean rebuildAll) {
        HistoryManager.setHistoryManagerInstance(input, rebuildAll);
        try {
            HistoryManager.getInstance().save();
        } catch (Exception ignored) {
        }
    }

    @Test
    void whenAttemptingToSettingInstanceTwice_thenItShouldFail() {
        HistoryManager.setHistoryManagerInstance("", true);
        // assert here
        Assertions.assertThrows(InstanceInitialisationException.class, ()-> HistoryManager.setHistoryManagerInstance("", true));
    }

    @Test
    void whenItsNewFile_thenItShouldRebuild() {
        String input = "newRandomName" + Math.random() + ".md";
        boolean rebuildAll = false;
        init(input, rebuildAll);
        // assert here
        assertTrue(HistoryManager.getInstance().shouldBeRebuild(input));
    }

    @Test
    void whenBuildAllIsTrue_thenItShouldRebuild() {
        String input = "whatever.md";
        boolean rebuildAll = true;
        init(input, rebuildAll);
        // assert here
        assertTrue(HistoryManager.getInstance().shouldBeRebuild(input));
    }

    @Test
    void whenBuildAllIsFalse_thenItShouldRebuild() {
        String input = "whatever.md";
        boolean rebuildAll = false;
        init(input, rebuildAll);
        // assert here
        assertFalse(HistoryManager.getInstance().shouldBeRebuild(input));
    }

/*
    @Test
    void whenSameFileTwice_thenItShouldNOTdRebuild() {
        String input = TestConfig.MD_FILE;
        boolean rebuildAll = false;
        init(input, rebuildAll);
        // assert here
        assertFalse(HistoryManager.getInstance().shouldBeRebuild(input));
    }

    @Test
    void whenSameFileTwiceButWrongPath_thenItShouldRebuild() {
        HistoryManager.getInstance().shouldBeRebuild("newRandomName.md");
        assertTrue(HistoryManager.getInstance().shouldBeRebuild("newRandomName.md"));
    }

    @Test
    void whenModifyFile_thenItShouldRebuild() {
        HistoryManager.getInstance().shouldBeRebuild(TestConfig.BASIC_MD_FILE_FOR_INCREMENTAL);
        try {
            FilesUtils.copyFileFromInputToOutput(TestConfig.BASIC_MD_TEST, TestConfig.BASIC_MD_FILE_FOR_INCREMENTAL);
        } catch (IOException e) {
            System.err.println("File not found. Please scan in new file.");
        }
        assertTrue(HistoryManager.getInstance().shouldBeRebuild(TestConfig.BASIC_MD_FILE_FOR_INCREMENTAL));
    }



    @Test
    void updateLastModificationsFile() {
    }

*/
}