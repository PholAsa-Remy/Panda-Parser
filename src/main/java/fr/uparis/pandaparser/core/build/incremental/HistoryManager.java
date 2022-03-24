package fr.uparis.pandaparser.core.build.incremental;
import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.utils.FilesUtils;
import java.io.*;
import java.util.HashMap;

public final class HistoryManager {
    private static HistoryManager instance;
    private static HashMap<String, Long> filesList = new HashMap<>();

    public static HistoryManager getInstance() {
        if (instance == null) {
            instance = new HistoryManager();
        }
        return instance;
    }
    private HistoryManager() {
        filesList = loadLastModificationRecorded();
    }

    @SuppressWarnings("unchecked")
    private HashMap<String, Long> loadLastModificationRecorded(){
        HashMap<String, Long> map = new HashMap<>();
        try {
            FileInputStream fis = new FileInputStream(Config.RECORDER_FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            map = (HashMap<String, Long> ) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }
        return map;
    }

    public Boolean shouldBeRebuild(String filePath) {
        Long lastModificationRecorded = filesList.get(filePath);
        if(lastModificationRecorded == null ){
            try {
                updateRecorderFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        try {
            return FilesUtils.getFileLastModificationDate(filePath) > lastModificationRecorded;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void updateRecorderFile(String filePath) throws IOException {
        Long newLastModificationRecorded = FilesUtils.getFileLastModificationDate(filePath);
        filesList.put(filePath,newLastModificationRecorded);
        saveLastModificationRecorded(filesList);
    }

    private void saveLastModificationRecorded(HashMap<String, Long> map) throws IOException {
        FileOutputStream fos = new FileOutputStream(Config.RECORDER_FILENAME);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(map);
        oos.close();
        fos.close();
    }
}