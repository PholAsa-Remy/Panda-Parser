package fr.uparis.pandaparser.core.build.incremental;
import fr.uparis.pandaparser.config.TestConfig;
import fr.uparis.pandaparser.exception.InstanceAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;


class HistoryManagerTest {
    @Test
    void whenAttemptingToSettingInstanceTwice_thenInstanceAlreadyExistsException() {
        HistoryManager.setHistoryManagerInstance("", true);
        Assertions.assertThrows(InstanceAlreadyExistsException.class, ()-> HistoryManager.setHistoryManagerInstance("", true));
    }
}