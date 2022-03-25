package fr.uparis.pandaparser.core.build.incremental;

import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.utils.FilesUtils;
import java.io.*;
import java.util.HashMap;
import static fr.uparis.pandaparser.config.Config.HISTORY_FILE_SIMPLE_PATH;
import static fr.uparis.pandaparser.config.Config.HISTORY_FILE_SITE_NAME;

public final class HistoryManager {
    private static HistoryManager instance;
    private static HashMap<String, Long> filesList = new HashMap<>();
    private static boolean rebuildAll;
    private static String historyFilePath;

    public static HistoryManager getInstance(String input, boolean rebuildAll) {
        if (instance == null) {
            instance = new HistoryManager(input, rebuildAll);
        }
        return instance;
    }

    private HistoryManager(String input, boolean rebuildAll_) {
        rebuildAll = rebuildAll_;
        historyFilePath = getHistoryFilePathFromInput(input);
    }

    @SuppressWarnings("unchecked")
    public static void loadHistoryFile() {
        HashMap<String, Long> map = new HashMap<>();
        try {
            FileInputStream fis = new FileInputStream(historyFilePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            map = (HashMap<String, Long>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }
        filesList = map;
    }


    public static Boolean shouldBeRebuild(String filePath) {
        if(rebuildAll) return true;
        Long lastRecorded = filesList.get(filePath);
        if(lastRecorded == null) return true;
        try {
            long newRecorded = FilesUtils.getFileLastModificationDate(filePath);
            if(newRecorded > lastRecorded) {
                filesList.put(filePath, newRecorded);
                return true;
            }
        } catch (IOException e) {return true;}
        return false;
    }

    public static void saveHistoryFile() throws IOException {
        FileOutputStream fos = new FileOutputStream(historyFilePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(filesList);
        oos.close();
        fos.close();
    }

    private String getHistoryFilePathFromInput(String input) {
        ParserType type = ParserType.getType(input);
        if (type.equals(ParserType.SITE) && !input.endsWith(File.separator))
            input += File.separator;
        return type.equals(ParserType.SITE) ? input + HISTORY_FILE_SITE_NAME : HISTORY_FILE_SIMPLE_PATH;
    }
}