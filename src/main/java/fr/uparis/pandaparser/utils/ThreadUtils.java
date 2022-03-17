package fr.uparis.pandaparser.utils;

import lombok.extern.java.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Log
public class ThreadUtils {

    public static void logFuture(final Future<String> future) {
        try {
            log.info(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void logAllFutures(final List<Future<String>> futures) {
        futures.forEach(ThreadUtils::logFuture);
    }

}
