package fr.uparis.pandaparser.core.build.incremental;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    private void init(String input, Boolean rebuildAll){
        //HistoryManager.getInstance(input, rebuildAll);
        //HistoryManager.loadHistoryFile();
//        try {
//            HistoryManager.saveHistoryFile();
//        } catch (Exception ignored) {}
    }

    @Test
    void whenItsNewFile_thenItShouldRebuild() {
        String input = "newRandomName"+Math.random()+".md";
        boolean rebuildAll = false;
        init(input, rebuildAll);
        // assert here
        assertTrue(HistoryManager.getInstance().shouldBeRebuild(input));
    }
/*
    @Test
    void whenSameFileTwice_thenItShouldNOTdRebuild() {
        String input = BASIC_MD_FILE_FOR_INCREMENTAL;
        boolean rebuildAll = false;
        init(input, rebuildAll);
        // assert here
        HistoryManager.shouldBeRebuild(input);
        assertFalse(HistoryManager.shouldBeRebuild(input));
    }

    @Test
    void whenSameFileTwiceButWrongPath_thenItShouldRebuild() {
        HistoryManager.shouldBeRebuild("newRandomName.md");
        assertTrue(HistoryManager.shouldBeRebuild("newRandomName.md"));
    }

    @Test
    void whenModifyFile_thenItShouldRebuild() {
        HistoryManager.shouldBeRebuild(BASIC_MD_FILE_FOR_INCREMENTAL);
        try {
            FilesUtils.copyFileFromInputToOutput(BASIC_MD_TEST, BASIC_MD_FILE_FOR_INCREMENTAL);
        } catch (IOException e) {
            System.err.println("File not found. Please scan in new file.");
        }
        assertTrue(HistoryManager.shouldBeRebuild(BASIC_MD_FILE_FOR_INCREMENTAL));
    }

//    void whenCreateNewFile_thenItShouldRebuild() {
//        HistoryManager.shouldBeRebuild(BASIC_MD_TEST_FOR_INCREMENTAL);
//    }


//    @Test
//    void getInstance() {
//    }


    @Test
    void updateLastModificationsFile() {
    }

 */
}