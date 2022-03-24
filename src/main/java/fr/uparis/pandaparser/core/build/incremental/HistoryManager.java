package fr.uparis.pandaparser.core.build.incremental;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.utils.FilesUtils;

import java.io.*;
import java.util.HashMap;

public final class HistoryManager {
    private static HistoryManager instance;
    private static HashMap<String, Long> filesList = new HashMap<>();
    private static boolean rebuildAll;

    public static HistoryManager getInstance(boolean rebuildAll) {
        if (instance == null) {
            instance = new HistoryManager(rebuildAll);
        }
        return instance;
    }

    private HistoryManager(boolean rebuildAll) {
        filesList = loadLastModificationRecorded();
        HistoryManager.rebuildAll = rebuildAll;
    }

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    private HashMap<String, Long> loadLastModificationRecorded() {
        HashMap<String, Long> map = new HashMap<>();
        try {
            FileInputStream fis = new FileInputStream(Config.RECORDER_FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            map = (HashMap<String, Long>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }
        return map;
    }


    public static Boolean shouldBeRebuild(String filePath) {
        try {
            return rebuildAll || !filesList.get(filePath).equals(FilesUtils.getFileLastModificationDate(filePath));
        } catch (IOException | NullPointerException e) {
            System.out.println("Dans le catch");
        }
        return true; // yes rebuild
    }

    /**
     * Update the lastModificationFile.ser file with the new date of the file
     *
     * @param filePath
     * @throws IOException
     */
    public static void updateLastModificationsFile(String filePath) throws IOException {
        Long newLastModificationRecorded = FilesUtils.getFileLastModificationDate(filePath);
        filesList.put(filePath, newLastModificationRecorded);
        FileOutputStream fos = new FileOutputStream(Config.RECORDER_FILENAME);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(filesList);
        oos.close();
        fos.close();
        System.out.println("OK lastModificationsFile has just been modified");
    }
}