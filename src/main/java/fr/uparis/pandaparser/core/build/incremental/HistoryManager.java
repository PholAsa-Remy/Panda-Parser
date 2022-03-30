package fr.uparis.pandaparser.core.build.incremental;

import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.exception.InstanceAlreadyExistsException;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static fr.uparis.pandaparser.config.Config.HISTORY_FILE_SIMPLE_PATH;
import static fr.uparis.pandaparser.config.Config.HISTORY_FILE_SITE_NAME;

@Data
@RequiredArgsConstructor
@Log
public final class HistoryManager implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    private static HistoryManager instance;

    private Map<String, Long> filesLastModifiedMap;
    private boolean rebuildAll;
    private String historyFilePath;

    public HistoryManager(String historyFilePath, Boolean rebuildAll) {
        this.historyFilePath=historyFilePath;
        this.rebuildAll=rebuildAll;
        this.filesLastModifiedMap=new HashMap<>();
    }

    public static HistoryManager getInstance() {
        return instance;
    }

    public static void setHistoryManagerInstance(@NonNull String input, @NonNull Boolean rebuildAll) {
        if(instance==null){
            instance=loadInstanceFromHistoryFile(getHistoryFilePathFromInput(input), rebuildAll);
            return;
        }
        throw new InstanceAlreadyExistsException("Instance is already exist");
    }

    private static HistoryManager loadInstanceFromHistoryFile(@NonNull String input, @NonNull Boolean rebuildAll) {

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(input))){
            return (HistoryManager) objectInputStream.readObject();
        }catch (ClassNotFoundException | IOException ioException){
            return new HistoryManager(input, rebuildAll);
        }
    }


    public Boolean shouldBeRebuild(String filePath) {
        if(this.rebuildAll) return true;
        try {
            Long lastRecorded = this.filesLastModifiedMap.get(filePath);
            long newRecorded = FilesUtils.getFileLastModificationDate(filePath);
            if(newRecorded > lastRecorded) {
                this.filesLastModifiedMap.put(filePath, newRecorded);
                return true;
            }
        } catch (Exception exception) {
            return true;
        }
        return false;
    }

    public void save(){
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(this.historyFilePath))){
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
        }catch (Exception exception){
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