package fr.uparis.pandaparser.utils;

import lombok.Builder;

import java.io.File;


@Builder
public final class PandaParserPath {

    private final String hostPath;
    private final String filePath;

    public PandaParserPath clone(String path) {
        return new PandaParserPath(hostPath, path.replaceFirst(this.hostPath, ""));
    }

    public String path() {
        String path =  ((this.hostPath == null) ? "" : this.hostPath);
        if (path.endsWith(File.separator))
            path += File.separator;
        path += (this.filePath == null) ? "" : this.filePath;
        return path;
    }


}
