package fr.uparis.pandaparser.utils;

import lombok.extern.java.Log;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Log
public class ThreadUtils {

    private void logFuture(Future<String> future) {
        try {
            log.info(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
