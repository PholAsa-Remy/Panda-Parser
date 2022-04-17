package fr.uparis.pandaparser.core.serve.watchdog;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.core.build.PandaParser;
import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import static fr.uparis.pandaparser.config.Config.DEFAULT_CONTENT_DIR;
import static java.nio.file.StandardWatchEventKinds.*;

@Log
public class DirectoryWatcher implements Callable<String> {

    private final String inputDirectory;
    private final String output;
    private WatchService watchService;

    private final Map<WatchKey, Path> folders = new ConcurrentHashMap<>();


    public DirectoryWatcher(String inputDirectory, String output) {
        this.inputDirectory = inputDirectory;
        this.output = output;
    }

    @Override
    public String call() throws Exception {
        this.watchService = FileSystems.getDefault().newWatchService();
        Path path = Path.of(this.inputDirectory);
        registerFolder(path);
        path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        return this.watch();
    }

    private String watch() {
        try {
            WatchKey watchKey;
            while ((watchKey = this.watchService.take()) != null) {
                WatchKey finalWatchKey = watchKey;
                watchKey.pollEvents().forEach(event -> {
                    if (!event.context().toString().contains("~")) {

                        Path filepath = (Path) event.context();
                        Path folder = folders.get(finalWatchKey);

                        PandaParser.builder()
                                .setInput(inputDirectory)
                                .setOutput(output)
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

    /**
     * Register the given directory, and all its sub-directories, with the
     * WatchService.
     */
    private void registerFolder(final Path folder) throws IOException {
        // register directory and subdirectories
        Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException
            {
                WatchKey key = dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                log.info("registering folder "+ dir);
                folders.put(key, dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
