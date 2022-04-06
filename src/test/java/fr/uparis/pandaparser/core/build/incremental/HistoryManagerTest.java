package fr.uparis.pandaparser.core.build.incremental;
import fr.uparis.pandaparser.config.TestConfig;
import fr.uparis.pandaparser.exception.InstanceAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
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
    void whenAttemptingToSettingInstanceTwice_thenInstanceAlreadyExistsException() {
        HistoryManager.setHistoryManagerInstance("", true);
        Assertions.assertThrows(InstanceAlreadyExistsException.class, ()-> HistoryManager.setHistoryManagerInstance("", true));
    }


    @Test
    void whenItsNewFile_thenItShouldRebuild() {
        String input = "newRandomName" + Math.random() + ".md";
        boolean rebuildAll = false;
        init(input, rebuildAll);
        assertTrue(HistoryManager.getInstance().shouldBeRebuild(input));
    }

    @Test
    void whenBuildAllIsTrue_thenItShouldRebuild() {
        String input = "whatever.md";
        boolean rebuildAll = true;
        init(input, rebuildAll);
        assertTrue(HistoryManager.getInstance().shouldBeRebuild(input));
    }

    @Test
    void whenBuildAllIsFalse_thenItShouldRebuild() {
        String input = "whatever.md";
        boolean rebuildAll = false;
        init(input, rebuildAll);
        assertTrue(HistoryManager.getInstance().shouldBeRebuild(input));
    }


    @Test
    void whenModifyFile_thenItShouldRebuild() {
        File file =new File(TestConfig.BASIC_MD_TEST);
        boolean setLastModifiedFile= file.setLastModified(System.currentTimeMillis());
        HistoryManager.setHistoryManagerInstance(TestConfig.BASIC_MD_TEST, !setLastModifiedFile);
        assertTrue(HistoryManager.getInstance().shouldBeRebuild(TestConfig.BASIC_MD_TEST));
    }

    @Test
    void whenFileIsUpdated_thenCorrect() {
        File file =new File(TestConfig.BASIC_MD_TEST);
        boolean setLastModifiedFile= file.setLastModified(System.currentTimeMillis());
        HistoryManager.setHistoryManagerInstance(TestConfig.BASIC_MD_TEST, !setLastModifiedFile);
        assertTrue(HistoryManager.getInstance().fileIsUpdated(TestConfig.BASIC_MD_TEST));
    }

}