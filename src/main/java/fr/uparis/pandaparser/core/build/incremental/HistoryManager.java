package fr.uparis.pandaparser.core.build.incremental;

import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.exception.InstanceAlreadyExistsException;
import fr.uparis.pandaparser.exception.InstanceInitialisationException;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.java.Log;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static fr.uparis.pandaparser.config.Config.HISTORY_FILE_SIMPLE_PATH;
import static fr.uparis.pandaparser.config.Config.HISTORY_FILE_SITE_NAME;

@Log
@Data
public final class HistoryManager implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /* instance */
    private volatile static HistoryManager INSTANCE;

    private boolean rebuildAll;
    private String historyFilePath;
    private Map<String, Long> filesLastModifiedMap;

    private HistoryManager(String historyFilePath, Boolean rebuildAll) {
        this.rebuildAll = rebuildAll;
        this.filesLastModifiedMap = new HashMap<>();
        this.historyFilePath = historyFilePath;
    }

    public static HistoryManager getInstance() {
        if (INSTANCE == null)
            throw new InstanceInitialisationException("instance must be initialised with setHistoryManagerInstance");
        return INSTANCE;
    }

    public static void setHistoryManagerInstance(@NonNull String input, @NonNull Boolean rebuildAll) {
        if (INSTANCE == null) {
            INSTANCE = loadInstanceFromHistoryFile(getHistoryFilePathFromInput(input), rebuildAll);
            return;
        }
        throw new InstanceAlreadyExistsException("Instance is already exist");
    }

    private static HistoryManager loadInstanceFromHistoryFile(@NonNull String input, @NonNull Boolean rebuildAll) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(input))) {
            return (HistoryManager) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException ioException) {
            return new HistoryManager(input, rebuildAll);
        }
    }

    public Boolean shouldBeRebuild(String filePath) {
        return this.rebuildAll || fileIsUpdated(filePath);
    }

    public boolean fileIsUpdated(String filePath) {
        try {
            return !this.filesLastModifiedMap.containsKey(filePath) || !Objects.equals(this.filesLastModifiedMap.get(filePath), FilesUtils.getFileLastModificationDate(filePath));
        } catch (IOException ioException) {
            return true;
        }
    }

    public void update(String filepath) {
        try {
            this.filesLastModifiedMap.put(filepath, FilesUtils.getFileLastModificationDate(filepath));
        } catch (IOException ignored) {
        }
    }

    public void save() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(this.historyFilePath))) {
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
        } catch (Exception exception) {
            log.warning("save history file failed");
        }
    }

    private static String getHistoryFilePathFromInput(String input) {
        ParserType type = ParserType.getType(input);
        if (type.equals(ParserType.SITE) && !input.endsWith(File.separator))
            input += File.separator;
        return type.equals(ParserType.SITE) ? input + HISTORY_FILE_SITE_NAME : HISTORY_FILE_SIMPLE_PATH;
    }
}