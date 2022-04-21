package fr.uparis.pandaparser.core.build.incremental;

import fr.uparis.pandaparser.exception.InstanceAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class HistoryManagerTest {
    @Test
    void whenAttemptingToSettingInstanceTwice_thenInstanceAlreadyExistsException() {
        HistoryManager.setHistoryManagerInstance("", true);
        Assertions.assertThrows(InstanceAlreadyExistsException.class, () -> HistoryManager.setHistoryManagerInstance("", true));
    }
}