package fr.uparis.pandaparser.core.serve.watchdog;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.core.build.PandaParser;
import lombok.extern.java.Log;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.Callable;

import static fr.uparis.pandaparser.config.Config.DEFAULT_CONTENT_DIR;
import static java.nio.file.StandardWatchEventKinds.*;

@Log
public class DirectoryWatcher implements Callable<String> {

    private final String inputDirectory;
    private final String output;
    private WatchService watchService;

    public DirectoryWatcher(String inputDirectory, String output) {
        this.inputDirectory = inputDirectory;
        this.output = output;
    }

    @Override
    public String call() throws Exception {
        this.watchService = FileSystems.getDefault().newWatchService();
        Path path = Path.of(this.inputDirectory);
        path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        return this.watch();
    }

    private String watch() {
        try {
            WatchKey watchKey;
            while ((watchKey = this.watchService.take()) != null) {
                watchKey.pollEvents().forEach(event -> {
                    if (!event.context().toString().contains("~")) {
                        PandaParser.builder()
                                .setInput(this.inputDirectory + File.separator + event.context())
                                .setOutput(output + File.separator + DEFAULT_CONTENT_DIR)
                                .build()
                                .parse();
                    }
                });
                watchKey.reset();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "end - watch";
    }
}
