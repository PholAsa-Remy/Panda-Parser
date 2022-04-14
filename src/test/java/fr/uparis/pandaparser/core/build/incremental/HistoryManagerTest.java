package fr.uparis.pandaparser.core.build.incremental;

import fr.uparis.pandaparser.exception.InstanceAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class HistoryManagerTest {
    private final HistoryManager hm = init();

    private HistoryManager init() {
        try {
            return HistoryManager.getInstance();
        } catch (Exception e) {
            HistoryManager.setHistoryManagerInstance("", false);
            return HistoryManager.getInstance();
        }
    }


    @Test
    void whenAttemptingToSettingInstanceTwice_thenInstanceAlreadyExistsException() {
        hm.setting("", true);
        Assertions.assertThrows(InstanceAlreadyExistsException.class, ()-> HistoryManager.setHistoryManagerInstance("", true));
    }


    @Test
    void whenItsNewFile_thenItShouldRebuild() {
        String input = "newRandomName" + Math.random() + ".md";
        boolean rebuildAll = false;
        hm.setting(input,rebuildAll);
        assertTrue(HistoryManager.getInstance().shouldBeRebuild(input));
    }

    @Test
    void whenBuildAllIsTrue_thenItShouldRebuild() {
        String input = "whatever.md";
        boolean rebuildAll = true;
        hm.setting(input,rebuildAll);
        assertTrue(HistoryManager.getInstance().shouldBeRebuild(input));
    }

    @Test
    void whenBuildAllIsFalse_thenItShouldRebuild() {
        String input = "whatever.md";
        boolean rebuildAll = false;
        hm.setting(input,rebuildAll);
        assertTrue(HistoryManager.getInstance().shouldBeRebuild(input));
    }


    @Test
    void whenModifyFile_thenItShouldRebuild() {
        File file =new File(TestConfig.BASIC_MD_TEST);
        boolean setLastModifiedFile= file.setLastModified(System.currentTimeMillis());
        hm.setting(TestConfig.BASIC_MD_TEST, !setLastModifiedFile);
        assertTrue(HistoryManager.getInstance().shouldBeRebuild(TestConfig.BASIC_MD_TEST));
    }

    @Test
    void whenFileIsUpdated_thenCorrect() {
        File file =new File(TestConfig.BASIC_MD_TEST);
        boolean setLastModifiedFile= file.setLastModified(System.currentTimeMillis());
        hm.setting(TestConfig.BASIC_MD_TEST, !setLastModifiedFile);
        assertTrue(HistoryManager.getInstance().fileIsUpdated(TestConfig.BASIC_MD_TEST));
    }

}