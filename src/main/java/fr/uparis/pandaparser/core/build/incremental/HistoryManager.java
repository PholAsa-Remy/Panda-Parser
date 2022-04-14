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


/**
 * Traduction incrémentale
 *
 * <p>The target files are only regenerated when the source files
 * on which they depend have been modified</p>
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since April 2022
 */

@Log
@Data
public final class HistoryManager implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /* instance of History Manager */
    private volatile static HistoryManager INSTANCE;

    private boolean rebuildAll;
    private String historyFilePath;
    private Map<String, Long> filesLastModifiedMap;


    private HistoryManager(String historyFilePath, Boolean rebuildAll) {
        this.rebuildAll = rebuildAll;
        this.filesLastModifiedMap = new HashMap<>();
        this.historyFilePath = historyFilePath;
    }

    /**
     * Get instance of History Manager
     *
     * @return instance of HistoryManager
     */
    public static HistoryManager getInstance() {
        if (INSTANCE == null)
            throw new InstanceInitialisationException("instance must be initialised with setHistoryManagerInstance");
        return INSTANCE;
    }

    /**
     * Set instance of HistoryManager
     *
     * @param input      file or site to build
     * @param rebuildAll define if we rebuild all or not
     */
    public static void setHistoryManagerInstance(@NonNull String input, @NonNull Boolean rebuildAll) {
        if (INSTANCE == null) {
            INSTANCE = loadInstanceFromHistoryFile(getHistoryFilePathFromInput(input), rebuildAll);
            return;
        }
        throw new InstanceAlreadyExistsException("Instance is already exist");
    }

    /**
     * Read instance HistoryManager from historyFile
     *
     * @param input      file or site to build
     * @param rebuildAll define if we rebuild all or not
     * @return HistoryManager
     */
    private static HistoryManager loadInstanceFromHistoryFile(@NonNull String input, @NonNull Boolean rebuildAll) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(input))) {
            return (HistoryManager) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException ioException) {
            return new HistoryManager(input, rebuildAll);
        }
    }

    /**
     * Get path of historyFile
     *
     * @param input the name of historyFile
     * @return path
     */
    private static String getHistoryFilePathFromInput(String input) {
        ParserType type = ParserType.getType(input);
        if (type.equals(ParserType.SITE) && !input.endsWith(File.separator))
            input += File.separator;
        return type.equals(ParserType.SITE) ? input + HISTORY_FILE_SITE_NAME : HISTORY_FILE_SIMPLE_PATH;
    }

    /**
     * Indicates if the file should be re-build
     *
     * @param filePath file Path
     * @return boolean
     */
    public Boolean shouldBeRebuild(String filePath) {
        return this.rebuildAll || fileIsUpdated(filePath);
    }

    /**
     * Indicates if the file is updated
     *
     * @param filePath file Path
     * @return boolean
     */
    public boolean fileIsUpdated(String filePath) {
        try {
            return !this.filesLastModifiedMap.containsKey(filePath) || !Objects.equals(this.filesLastModifiedMap.get(filePath), FilesUtils.getFileLastModificationDate(filePath));
        } catch (IOException ioException) {
            return true;
        }
    }

    /**
     * Add the file to build on the map
     *
     * @param filepath file path
     */
    public void update(String filepath) {
        try {
            this.filesLastModifiedMap.put(filepath, FilesUtils.getFileLastModificationDate(filepath));
        } catch (IOException ignored) {
        }
    }

    /**
     * Save the instance of HistoryManager in the historyFile
     */
    public void save() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(this.historyFilePath))) {
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
        } catch (Exception exception) {
            log.warning("save history file failed");
        }
    }

    /**
     * Get path of historyFile
     *
     * @param input the name of historyFile
     * @return path
     */
    private static String getHistoryFilePathFromInput(String input) {
        ParserType type = ParserType.getType(input);
        if (type.equals(ParserType.SITE) && !input.endsWith(File.separator))
            input += File.separator;
        return type.equals(ParserType.SITE) ? input + HISTORY_FILE_SITE_NAME : HISTORY_FILE_SIMPLE_PATH;
    }

    public void setting(String historyFilePath, Boolean rebuildAll) {
        this.rebuildAll = rebuildAll;
        this.filesLastModifiedMap = new HashMap<>();
        this.historyFilePath = historyFilePath;
    }

}